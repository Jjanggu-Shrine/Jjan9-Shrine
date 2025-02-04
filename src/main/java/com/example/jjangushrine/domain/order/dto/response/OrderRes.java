package com.example.jjangushrine.domain.order.dto.response;

import java.util.List;

public record OrderResDto(
        int originalTotalAmount,
        int discountedTotalAmount,
        List<OrderItemResDto> orderItems,
        String shippingAddress
        ) {
}
