package com.example.jjangushrine.domain.order.repository;

import com.example.jjangushrine.domain.order.entity.Order;
import com.example.jjangushrine.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
