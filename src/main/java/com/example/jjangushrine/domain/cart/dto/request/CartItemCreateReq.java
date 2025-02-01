package com.example.jjangushrine.domain.cart.dto.request;


public record CartItemCreateReq(Long userId, Long productId, int quantity) {

}
