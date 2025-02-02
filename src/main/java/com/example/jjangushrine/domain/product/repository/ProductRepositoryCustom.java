package com.example.jjangushrine.domain.product.repository;

public interface ProductRepositoryCustom {

	boolean existsByProductIdAndSellerId(Long productId, Long sellerId);
}
