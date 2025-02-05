package com.example.jjangushrine.domain.cart.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;


public record CartItemCreateReq(
        @JsonProperty("productId") Long productId,
        @JsonProperty("quantity") Short quantity
) {
}
