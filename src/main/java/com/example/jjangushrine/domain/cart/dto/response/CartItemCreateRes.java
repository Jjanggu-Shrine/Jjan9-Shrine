package com.example.jjangushrine.domain.cart.dto.response;


public record CartItemCreateRes(String cartId, Long productId, String productName, int quantity, int totalPrice) {
}
