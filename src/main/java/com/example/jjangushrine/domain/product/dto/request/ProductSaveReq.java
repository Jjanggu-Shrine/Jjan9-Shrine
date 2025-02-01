package com.example.jjangushrine.domain.product.dto.request;

public record ProductSaveReq(
	Long storeId,
	String name,
	Integer amount,
	String description,
	String image,
	Short stock,
	String category
) {
}
