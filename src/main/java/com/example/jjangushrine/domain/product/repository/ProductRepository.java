package com.example.jjangushrine.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jjangushrine.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}