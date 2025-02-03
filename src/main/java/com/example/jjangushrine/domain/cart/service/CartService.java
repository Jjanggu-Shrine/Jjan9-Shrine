package com.example.jjangushrine.domain.cart.service;

import com.example.jjangushrine.domain.cart.dto.request.CartItemCreateReq;
import com.example.jjangushrine.domain.cart.dto.response.CartItemCreateRes;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.exception.common.LockException;
import com.example.jjangushrine.exception.common.Threadxception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;
    private final RedissonClient redissonClient; // 분산락을 사용하기 위해 추가


    /**
     * 장바구니 아이템 추가
     * @param reqDto cartId, productId, Quantity
     * @return cartId, productId, ProductName, Quantity, totalPrice
     */
    public CartItemCreateRes addCartItem(CartItemCreateReq reqDto) {
        String userId = reqDto.cartId().toString();
        String cartKey = CreateCartKey(userId); // redis 장바구나 키
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
            Product product = productRepository.findById(reqDto.productId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

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

    private String CreateCartKey(String userId) {

        String cartKey = "cart:" + userId;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
            redisTemplate.opsForHash().put(cartKey, "init", "true");
        }

        return cartKey;
    }

}
