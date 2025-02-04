package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ForbiddenException(ErrorCode errorCode, String detail){
        super(errorCode, detail);
    }
}
