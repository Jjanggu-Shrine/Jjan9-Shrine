package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class OrderNoDeivered extends BusinessException {
    public OrderNoDeivered(){super(ErrorCode.ORDER_NOT_DELIVERED);
    }

    public OrderNoDeivered(String detail) {
        super(ErrorCode.ORDER_NOT_DELIVERED, detail);
    }

    public OrderNoDeivered(ErrorCode errorCode) {
        super(errorCode);
    }
}
