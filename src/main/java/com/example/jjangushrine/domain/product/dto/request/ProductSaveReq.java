package com.example.jjangushrine.domain.product.dto.request;
import com.example.jjangushrine.domain.product.dto.ProductValidationMessage;
import com.example.jjangushrine.domain.product.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductSaveReq(
	@NotBlank(message = ProductValidationMessage.STOREID_BLANK_MESSAGE)
	Long storeId,

	@NotBlank(message = ProductValidationMessage.NAME_BLANK_MESSAGE)
	@Size(max = 20, message = ProductValidationMessage.PRODUCT_NAME_MAX_MESSAGE)
	String name,

	@NotBlank(message = ProductValidationMessage.AMOUNT_BLANK_MESSAGE)
	Integer amount,

	@NotBlank(message = ProductValidationMessage.DESCRIPTION_BLANK_MESSAGE)
	String description,

	@NotBlank(message = ProductValidationMessage.IMAGE_BLANK_MESSAGE)
	String imageUrl,

	@NotBlank(message = ProductValidationMessage.STOCK_BLANK_MESSAGE)
	Short stock,

	@NotBlank(message = ProductValidationMessage.CATEGORY_BLANK_MESSAGE)
	String category
) {
	public Product toEntity() {
		return Product.builder()
			.name(name())
			.stock(stock())
			.image(imageUrl())
			.amount(amount())
			.description(description())
			.category(category())
			.build();
	}
}
