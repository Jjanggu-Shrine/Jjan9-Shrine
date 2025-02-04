package com.example.jjangushrine.domain.order.dto.response;

import java.util.List;

public record OrderRes(
        int originalTotalAmount,
        int discountedTotalAmount,
        List<OrderItemRes> orderItems,
        String shippingAddress
        ) {
}
