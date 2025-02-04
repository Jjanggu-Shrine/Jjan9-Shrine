package com.example.jjangushrine.domain.order.dto.response;

public record OrderItemRes(
        String productName,
        Short quantity,
        int productPrice
) {
}
