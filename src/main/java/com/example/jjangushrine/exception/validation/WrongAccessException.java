package com.example.jjangushrine.exception.validation;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class WrongAccessException extends BusinessException {
    public WrongAccessException() {
        super(ErrorCode.INVALID_ACCESS);
    }

    public WrongAccessException(String detail) {
        super(ErrorCode.INVALID_ACCESS, detail);
    }
}