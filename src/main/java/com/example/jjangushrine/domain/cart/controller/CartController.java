package com.example.jjangushrine.domain.cart.controller;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.cart.dto.request.CartItemCreateReq;
import com.example.jjangushrine.domain.cart.dto.response.CartItemCreateRes;
import com.example.jjangushrine.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 아이템 추가
     * @param reqDto reqDto cartId, productId, Quantity
     * @return cartId, productId, ProductName, Quantity, totalPrice
     */
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemCreateRes>> addCartItem(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestBody CartItemCreateReq reqDto
    ) {
        CartItemCreateRes cartItemCreateRes = cartService.addCartItem(authUser, reqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "장바구니에 아이템이 추가되었습니다.",
                        cartItemCreateRes
                ));
    }

}
