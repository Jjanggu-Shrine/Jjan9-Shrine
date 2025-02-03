package com.example.jjangushrine.domain.product.exception;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
	public ProductNotFoundException() {
		super(ErrorCode.PRODUCT_NOT_FOUND);
	}
}
