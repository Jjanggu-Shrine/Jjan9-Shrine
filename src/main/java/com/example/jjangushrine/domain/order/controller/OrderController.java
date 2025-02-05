package com.example.jjangushrine.domain.order.controller;

import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.order.dto.response.OrderRes;
import com.example.jjangushrine.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성
     *
     * @param authUser
     * @param couponId
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderRes>> createOrder(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestParam(required = false) Long couponId
    ) {
        OrderRes createOrderRes = orderService.createOrder(authUser, couponId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "주문이 완료되었습니다.",
                        createOrderRes
                ));
    }

    /**
     * 주문취소
     *
     * @param authUser
     * @param orderId
     * @return
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestParam Long orderId
    ) {
        orderService.cancelOrder(authUser, orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "주문이 취소되었습니다."
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<OrderRes>> getOrderById(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestParam Long orderId
    ) {
        OrderRes orderById = orderService.getOrderById(authUser, orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "주문 조회",
                        orderById
                ));
    }
}
