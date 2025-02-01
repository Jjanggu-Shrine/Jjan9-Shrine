package com.example.jjangushrine.domain.product.dto.response;

import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.enums.Category;

public record ProductSaveRes(
	Long productId,
	Long storeId,
	String name,
	Integer amount,
	String description,
	String image,
	Short stock,
	Category category
) {

	public static ProductSaveRes fromEntity(Product product) {
		return new ProductSaveRes(
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
