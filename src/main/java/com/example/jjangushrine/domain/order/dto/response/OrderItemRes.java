package com.example.jjangushrine.domain.order.dto.response;

public record OrderItemResDto(
        String productName,
        int quantity,
        int productPrice
) {
}
