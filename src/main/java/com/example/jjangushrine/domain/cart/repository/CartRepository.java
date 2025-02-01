package com.example.jjangushrine.domain.cart.repository;

import com.example.jjangushrine.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
