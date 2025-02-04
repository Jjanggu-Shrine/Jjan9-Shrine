package com.example.jjangushrine.domain.order.dto.response;

public record OrderItemRes(
        String productName,
        int quantity,
        int productPrice
) {
}
