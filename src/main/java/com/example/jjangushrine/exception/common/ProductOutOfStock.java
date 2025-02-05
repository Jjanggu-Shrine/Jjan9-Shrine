package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class ProductOutOfStock extends BusinessException {
    public ProductOutOfStock() {
        super(ErrorCode.DUPLICATE_OUT_OF_STOCK);
    }
}
