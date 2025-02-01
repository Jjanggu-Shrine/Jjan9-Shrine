package com.example.jjangushrine.domain.cart.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemCreateReq {

    private final Long userId;
    private final Long productId;
    private final int quantity;
}
