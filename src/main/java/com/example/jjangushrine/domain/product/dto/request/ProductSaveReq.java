package com.example.jjangushrine.domain.product.dto.request;

import com.example.jjangushrine.domain.product.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductSaveReq(
	@NotBlank
	Long storeId,

	@NotBlank
	@Size(max = 20)
	String name,

	@NotBlank
	Integer amount,

	@NotBlank
	String description,

	@NotBlank
	String image,

	@NotBlank
	Short stock,

	@NotBlank
	String category
) {
	public Product toEntity() {
		return Product.builder()
			.name(name())
			.stock(stock())
			.image(image())
			.amount(amount())
			.description(description())
			.category(category())
			.build();
	}
}
