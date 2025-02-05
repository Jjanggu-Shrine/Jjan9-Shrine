package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class OrderException extends BusinessException {
    public OrderException(ErrorCode errorCode) {
        super(ErrorCode.DUPLICATE_CANCELED_ORDER);
    }
}
