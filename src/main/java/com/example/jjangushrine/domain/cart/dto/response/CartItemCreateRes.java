package com.example.jjangushrine.domain.cart.dto.response;


public record CartItemCreateRes(String cartId, Long productId, String productName, Short quantity, int totalPrice) {
}
