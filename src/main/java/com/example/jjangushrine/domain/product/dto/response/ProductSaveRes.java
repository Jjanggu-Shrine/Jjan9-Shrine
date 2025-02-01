package com.example.jjangushrine.domain.product.dto.response;

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

}
