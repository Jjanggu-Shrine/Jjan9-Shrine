package com.example.jjangushrine.exception.auth;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class ForbiddenException extends BusinessException {
    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN_ACCESS);
    }

    public ForbiddenException(String detail) {
        super(ErrorCode.FORBIDDEN_ACCESS, detail);
    }
}