package com.example.jjangushrine.domain.order.repository;

import com.example.jjangushrine.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
