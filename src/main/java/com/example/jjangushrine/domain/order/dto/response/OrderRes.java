package com.example.jjangushrine.domain.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRes(
        Long orderId,
        String userName,
        String addressName,
        List<OrderItemRes> orderItems,
        int originalTotalAmount,
        int discountedTotalAmount,
        boolean couponUsed,
        LocalDateTime createAt
        ) {
}
