package com.example.jjangushrine.domain.order.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemCreateReq {

    private final Long productId;
    private final Long quantity;
}
