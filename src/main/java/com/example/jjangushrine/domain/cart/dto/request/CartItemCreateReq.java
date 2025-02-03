package com.example.jjangushrine.domain.cart.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;


public record CartItemCreateReq(
        @JsonProperty("cartId") Long cartId,
        @JsonProperty("productId") Long productId,
        @JsonProperty("quantity") int quantity
) {
}
