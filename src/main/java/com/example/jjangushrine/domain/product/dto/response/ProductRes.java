package com.example.jjangushrine.domain.product.dto.response;

import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.enums.Category;

public record ProductRes(
	Long productId,
	Long storeId,
	String name,
	Integer amount,
	String description,
	String image,
	Short stock,
	Category category
) {

	public static ProductRes fromEntity(Product product) {
		return new ProductRes(
			product.getId(),
			product.getStore().getId(),
			product.getName(),
			product.getAmount(),
			product.getDescription(),
			product.getImage(),
			product.getStock(),
			product.getCategory()
		);
	}
}
