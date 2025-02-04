package com.example.jjangushrine.domain.product.exception;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class ProductOutOfStock extends BusinessException {
    public ProductOutOfStock() {
        super(ErrorCode.DUPLICATE_OUT_OF_STOCK);

    }
}
