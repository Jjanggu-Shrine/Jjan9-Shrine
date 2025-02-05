package com.example.jjangushrine.domain.order.service;

import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.address.entity.Address;
import com.example.jjangushrine.domain.address.service.AddressService;
import com.example.jjangushrine.domain.coupon.entity.UserCoupon;
import com.example.jjangushrine.domain.coupon.repository.UserCouponRepository;
import com.example.jjangushrine.domain.order.dto.response.OrderItemRes;
import com.example.jjangushrine.domain.order.dto.response.OrderRes;
import com.example.jjangushrine.domain.order.entity.Order;
import com.example.jjangushrine.domain.order.entity.OrderItem;
import com.example.jjangushrine.domain.order.repository.OrderItemRepository;
import com.example.jjangushrine.domain.order.repository.OrderRepository;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.service.ProductService;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ForbiddenException;
import com.example.jjangushrine.exception.common.NotFoundException;
import com.example.jjangushrine.exception.common.OrderException;
import com.example.jjangushrine.exception.coupon.CouponException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserService userService;
    private final ProductService productService;
    private final UserCouponRepository userCouponRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressService addressService;

    /**
     * 주문 생성
     *
     * @param authUser
     * @param couponId
     * @return
     */
    @Transactional
    public OrderRes createOrder(CustomUserDetails authUser, Long couponId) {
        String userId = authUser.getId().toString();
        String cartKey = "cart:" + userId;

        Map<Object, Object> cartItems = redisTemplate.opsForHash().entries(cartKey);

        if (cartItems.isEmpty() || (cartItems.size() == 1 && cartItems.containsKey("init"))) {
            throw new NotFoundException(ErrorCode.CART_NOT_FOUND);
        }

        User user = userService.findUserByEmail(authUser.getEmail());
        Address address = addressService.findByUserId(authUser.getId(), authUser.getRole()); // 주소 조회
        Order order = Order.builder()
                .user(user)
                .build();
        int originalTotalAmount = 0; // 총 가격 초기화

        // 주문 아이템 저장

        List<OrderItemRes> orderItemList = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Object, Object> entry : cartItems.entrySet()) {
            String productKey = (String) entry.getKey();
            if ("init".equals(productKey)) continue;      // init 무시

            Long productId = Long.parseLong(productKey.replace("product:", ""));
            Integer quantity = (Integer) entry.getValue();

            Product product = productService.getProductById(productId);

            // 재고 체크 및 차감
            productService.decreaseStock(productId, quantity);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .productName(product.getName())
                    .quantity(quantity)
                    .build();

            originalTotalAmount += product.getAmount() * quantity;
            orderItems.add(orderItem);

            OrderItemRes orderItemRes = new OrderItemRes(
                    orderItem.getProductName(),
                    orderItem.getQuantity(),
                    orderItem.getProductPrice()
            );
            orderItemList.add(orderItemRes);
        }

        order.setOriginalTotalAmount(originalTotalAmount); // 쿠폰 적용이 되지 않은 오리지널 총 가격
        int discountTotalAmount = (originalTotalAmount); // 쿠폰 적용이 된 가격 초기화

        // 쿠폰 적용
        if (couponId != null) {
            UserCoupon userCoupon = userCouponRepository.findByUser_IdAndCoupon_CouponIdAndUsedAtIsNull(authUser.getId(), couponId)
                    .orElseThrow(() -> new CouponException(ErrorCode.COUPON_NOT_FOUND));

            int couponDiscountPercentage = userCoupon.getCoupon().getDiscountPercent();

            discountTotalAmount -= originalTotalAmount * couponDiscountPercentage / 100;
            order.setCouponUsed(true);
            order.setCouponId(couponId);

            // 쿠폰 사용 처리
            userCoupon.markAsUsed();
            userCouponRepository.save(userCoupon);
        } else {
            order.setCouponUsed(false);
        }

        order.setDiscountedTotalAmount(discountTotalAmount); // 쿠폰 적용 된 가격

        Order saveOrder = orderRepository.save(order);

        for (OrderItem item : orderItems) {
            OrderItem.builder()
                    .order(saveOrder)
                    .build();
            orderItemRepository.save(item);
        }
        // Redis 장바구니 초기화
        redisTemplate.delete(cartKey);

        return new OrderRes(
                saveOrder.getId(),
                authUser.getUsername(),
                address.getAddressName(),
                orderItemList,
                originalTotalAmount,
                discountTotalAmount,
                saveOrder.isCouponUsed(),
                saveOrder.getCreatedAt());
    }

    /**
     * 주문 취소 (재고 복구, 쿠폰 복구)
     *
     * @param authUser
     * @param orderId
     */
    @Transactional
    public void cancelOrder(CustomUserDetails authUser, Long orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(authUser.getId())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_ACCESS);
        }

        // 취소가 되어있는지 확인
        if (order.isCanceled()) {
            throw new OrderException(ErrorCode.DUPLICATE_CANCELED_ORDER);
        }

        // 주문 취소 완료
        order.setCanceled(true);

        // 주문 취소 시 재고 복구
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        for (OrderItem item : orderItems) {
            productService.increaseStock(item.getProduct().getId(), item.getQuantity());
        }

        // 주문 취소 시 쿠폰 사용 취소
        if (order.isCouponUsed()) {
            UserCoupon userCoupon = userCouponRepository.findByUser_IdAndCoupon_CouponId(authUser.getId(), order.getCouponId())
                    .orElseThrow(() -> new CouponException(ErrorCode.COUPON_NOT_FOUND));

            userCoupon.unmarkAsUsed();
            userCouponRepository.save(userCoupon);
        }

    }

    /**
     * 주문조회
     * @param authUser
     * @param orderId
     * @return
     */
    @Transactional(readOnly = true)
    public OrderRes getOrderById(CustomUserDetails authUser, Long orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORDER_NOT_FOUND));

        if (!order.getUser().getId().equals(authUser.getId())) {
            throw new ForbiddenException(ErrorCode.FORBIDDEN_ACCESS);
        }

        Address address = addressService.findByUserId(authUser.getId(), authUser.getRole());

        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        List<OrderItemRes> orderItemResList = orderItems.stream()
                .map(item -> new OrderItemRes(
                        item.getProductName(),
                        item.getQuantity(),
                        item.getProductPrice()
                ))
                .toList();
        return new OrderRes(
                order.getId(),
                authUser.getUsername(),
                address.getAddressName(),
                orderItemResList,
                order.getOriginalTotalAmount(),
                order.getDiscountedTotalAmount(),
                order.isCouponUsed(),
                order.getCreatedAt()
        );
    }
}
