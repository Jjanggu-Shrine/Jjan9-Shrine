package com.example.jjangushrine.domain.cart.repository;

import com.example.jjangushrine.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
