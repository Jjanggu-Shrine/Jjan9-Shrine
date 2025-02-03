package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class ConflictException extends BusinessException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ConflictException(ErrorCode errorCode, String detail){
        super(errorCode, detail);
    }
}
