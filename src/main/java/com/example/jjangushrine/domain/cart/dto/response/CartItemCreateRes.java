package com.example.jjangushrine.domain.cart.dto.response;


public record CartItemCreateRes(Long productId, String productName, int quantity, int price) {
}
