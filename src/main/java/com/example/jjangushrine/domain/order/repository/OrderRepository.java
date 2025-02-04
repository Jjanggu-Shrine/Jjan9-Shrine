package com.example.jjangushrine.domain.order.repository;

import com.example.jjangushrine.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
