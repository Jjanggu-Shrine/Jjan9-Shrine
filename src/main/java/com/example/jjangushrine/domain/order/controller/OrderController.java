package com.example.jjangushrine.domain.order.controller;

import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.order.dto.response.OrderRes;
import com.example.jjangushrine.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

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
}
