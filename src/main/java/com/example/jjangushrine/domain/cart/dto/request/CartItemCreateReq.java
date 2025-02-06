package com.example.jjangushrine.domain.cart.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record CartItemCreateReq(
        @NotBlank(message = "productId를 입력해주세요.")
        @JsonProperty("productId") Long productId,
        @NotBlank(message = "수량을 입력해주세요.")
        @JsonProperty("quantity") Short quantity
) {
}
