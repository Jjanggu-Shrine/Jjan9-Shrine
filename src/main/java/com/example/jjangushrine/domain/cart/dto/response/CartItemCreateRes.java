package com.example.jjangushrine.domain.cart.dto.response;

import lombok.Getter;

@Getter
public class CartItemCreateRes {
    private final Long productId;
    private final String productName;
    private final int quantity;
    private final int price;

    public CartItemCreateRes(Long productId, String productName, int quantity, int price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}
