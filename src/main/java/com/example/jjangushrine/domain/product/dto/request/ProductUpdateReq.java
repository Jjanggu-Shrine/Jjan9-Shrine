package com.example.jjangushrine.domain.product.dto.request;

import jakarta.validation.constraints.Size;

public record ProductUpdateReq(
	@Size(max = 20, message = "상품명은 최대 20자까지 가능합니다.")
	String name,

	Integer amount,

	String description,

	String image,

	Short stock,

	String category
) {
}
