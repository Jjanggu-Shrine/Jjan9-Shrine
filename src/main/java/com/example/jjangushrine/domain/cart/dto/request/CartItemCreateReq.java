package com.example.jjangushrine.domain.cart.dto.request;


import com.example.jjangushrine.domain.cart.dto.CartValidationMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record CartItemCreateReq(
        @NotBlank(message = CartValidationMessage.PRODUCT_BLANK_MESSAGE)
        @JsonProperty("productId") Long productId,
        @NotBlank(message = CartValidationMessage.QUANTITY_BLANK_MESSAGE)
        @JsonProperty("quantity") Short quantity
) {
}
