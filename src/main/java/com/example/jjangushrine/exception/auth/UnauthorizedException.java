package com.example.jjangushrine.exception.auth;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    public UnauthorizedException(String detail) {
        super(ErrorCode.UNAUTHORIZED_ACCESS, detail);
    }
}