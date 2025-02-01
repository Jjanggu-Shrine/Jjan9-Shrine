package com.example.jjangushrine.domain.product.dto.request;

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
}
