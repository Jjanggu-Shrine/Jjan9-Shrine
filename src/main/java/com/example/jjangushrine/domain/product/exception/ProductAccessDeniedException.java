package com.example.jjangushrine.domain.product.exception;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class ProductAccessDeniedException extends BusinessException {
	public ProductAccessDeniedException() {
		super(ErrorCode.PRODUCT_FORBIDDEN_ACCESS);
	}
}
