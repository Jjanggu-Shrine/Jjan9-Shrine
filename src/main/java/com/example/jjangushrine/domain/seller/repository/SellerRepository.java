package com.example.jjangushrine.domain.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jjangushrine.domain.seller.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
