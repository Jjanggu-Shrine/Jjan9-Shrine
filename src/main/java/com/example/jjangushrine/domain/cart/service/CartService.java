package com.example.jjangushrine.domain.cart.service;

import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.cart.dto.request.CartItemCreateReq;
import com.example.jjangushrine.domain.cart.dto.request.CartItemUpdateReq;
import com.example.jjangushrine.domain.cart.dto.response.CartItemCreateRes;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.service.ProductService;
import com.example.jjangushrine.exception.common.LockException;
import com.example.jjangushrine.exception.common.Threadxception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient; // 분산락을 사용하기 위해 추가
    private final ProductService productService;


    /**
     * 장바구니 아이템 추가
     *
     * @param reqDto cartId, productId, Quantity
     * @return cartId, productId, ProductName, Quantity, totalPrice
     */
    public CartItemCreateRes addCartItem(CustomUserDetails authUser, CartItemCreateReq reqDto) {
        String userId = authUser.getId().toString();
        String cartKey = createCartKey(userId); // redis 장바구나 키
        String lockKey = "lock:cart:" + userId;

        RLock lock = redissonClient.getLock(lockKey); // 락 가져오기

        boolean isLocked = false;
        try {
            // 5초 동안 락을 시도하고, 10초 동안 유지(자동 해제)
            isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new LockException();
            }

            // 상품 정보 조회
            Product product = productService.getProductById(reqDto.productId());

            // 상품 갯수 redis 캐시
            String productKey = "product:" + product.getId();
            Integer productQuantity = (Integer) redisTemplate.opsForHash().get(cartKey, productKey);

            // 수량 더하기
            int allQuantity = (productQuantity == null ? 0 : productQuantity) + reqDto.quantity();
            redisTemplate.opsForHash().put(cartKey, productKey, allQuantity);

            // 수량 * 상품단가
            int totalPrice = allQuantity * product.getAmount();

            return new CartItemCreateRes(cartKey, product.getId(), product.getName(), allQuantity, totalPrice);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Threadxception("상품 추가 중 인터럽트 발생");
        } finally {
            if (isLocked) {
                lock.unlock(); // 락 해제
            }
        }
    }

    // 장바구니가 없으면 자동으로 생성
    private String createCartKey(String userId) {

        String cartKey = "cart:" + userId;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
            redisTemplate.opsForHash().put(cartKey, "init", "true");
        }

        return cartKey;
    }

    /**
     * 아이템 수량 변경
     * @param authUser
     * @param reqDto
     * @return
     */
    public CartItemCreateRes updateCartItemQuantity(CustomUserDetails authUser, CartItemUpdateReq reqDto) {
        String userId = authUser.getId().toString();
        String cartKey = createCartKey(userId); // redis 장바구나 키
        String lockKey = "lock:cart:" + userId;

        RLock lock = redissonClient.getLock(lockKey); // 락 가져오기

        boolean isLocked = false;

        try {
            isLocked = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new LockException();
            }

            Product product = productService.getProductById(reqDto.productId());

            // Redis 장바구니에서 아이템 수량 조회
            String productKey = "product:" + product.getId();
            Integer productQuantity = (Integer) redisTemplate.opsForHash().get(cartKey, productKey);

            // 수량이 0으로 수정되면 아이템 삭제
            if (reqDto.quantity() <= 0) {
                redisTemplate.opsForHash().delete(cartKey, productKey);
                return new CartItemCreateRes(cartKey, product.getId(), product.getName(), 0, 0);
            }

            // 수량 변경
            redisTemplate.opsForHash().put(cartKey, productKey, reqDto.quantity());

            int totalPrice = reqDto.quantity() * product.getAmount();

            return new CartItemCreateRes(cartKey, product.getId(), product.getName(), reqDto.quantity(), totalPrice);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Threadxception("상품 추가 중 인터럽트 발생");
        } finally {
            if (isLocked) {
                lock.unlock(); // 락 해제
            }
        }
    }

    /**
     * 장바구니 조회
     * @param authUser
     * @return
     */
    public List<CartItemCreateRes> getCartItems(CustomUserDetails authUser) {
        String userId = authUser.getId().toString();
        String cartKey = createCartKey(userId);

        // Redis 에서 장바구니 아이템 전체 조회
        Map<Object, Object> cartItems = redisTemplate.opsForHash().entries(cartKey);

        List<CartItemCreateRes> resList = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : cartItems.entrySet()) {
            String productKey = (String) entry.getKey();

            // "init"은 장바구니가 존재하는지 않하는지의 여부 확인으로 Object 무시
            if ("init".equals(productKey)) continue;

            Long productId = Long.parseLong(productKey.replace("product:", ""));
            Integer quantity = (Integer) entry.getValue();

            Product product = productService.getProductById(productId);

            int totalPrice = product.getAmount() * quantity;

            resList.add(new CartItemCreateRes(cartKey, product.getId(), product.getName(), quantity, totalPrice));
        }
        return resList;
    }
}
