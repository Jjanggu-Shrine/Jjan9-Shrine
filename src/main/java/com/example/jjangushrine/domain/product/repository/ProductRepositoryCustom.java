package com.example.jjangushrine.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.jjangushrine.domain.product.dto.response.ProductRes;
import com.example.jjangushrine.domain.product.enums.Category;

public interface ProductRepositoryCustom {

	boolean existsByProductIdAndSellerId(Long productId, Long sellerId);
	Page<ProductRes> findAllProductByCategory(Category category, Pageable pageable);
	Page<ProductRes> findAllProductByStoreAndCategory(Long stroeId, Category category, Pageable pageable);
}
