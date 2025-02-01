package com.example.jjangushrine.domain.cart.controller;

import com.example.jjangushrine.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
}
