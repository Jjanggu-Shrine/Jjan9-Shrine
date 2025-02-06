package com.example.jjangushrine.domain.cart.controller;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.cart.dto.request.CartItemCreateReq;
import com.example.jjangushrine.domain.cart.dto.request.CartItemUpdateReq;
import com.example.jjangushrine.domain.cart.dto.response.CartItemCreateRes;
import com.example.jjangushrine.domain.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @Valid @RequestBody CartItemCreateReq reqDto
    ) {
        CartItemCreateRes cartItemCreateRes = cartService.addCartItem(authUser, reqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "장바구니에 아이템이 추가되었습니다.",
                        cartItemCreateRes
                ));
    }

    /**
     * 아이템 수량 변경 (갯수가 0이면 삭제)
     * @param authUser user
     * @param reqDto productId, quantity
     * @return cartId, productId, ProductName, Quantity, totalPrice
     */
    @PatchMapping("/items")
    public ResponseEntity<ApiResponse<CartItemCreateRes>> updateCartItem(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @Valid @RequestBody CartItemUpdateReq reqDto
    ) {
        CartItemCreateRes cartItemUpdateRes = cartService.updateCartItemQuantity(authUser, reqDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("아이템 수량이 변경되었습니다.",
                        cartItemUpdateRes));
    }

    /**
     * 장바구니 조회
     * @param authUser user
     * @return cartId, productId, productName, quantity, totalPrice
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemCreateRes>>> getCartItem(
            @AuthenticationPrincipal CustomUserDetails authUser
    ) {
        List<CartItemCreateRes> cartItems = cartService.getCartItems(authUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("장바구니를 조회합니다.",
                        cartItems));
    }
}
