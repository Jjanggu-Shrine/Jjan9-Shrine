package com.example.jjangushrine.exception.auth;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class LoginFailedException extends BusinessException {
    public LoginFailedException() {
        super(ErrorCode.LOGIN_FAILED);
    }

    public LoginFailedException(String detail) {
        super(ErrorCode.LOGIN_FAILED, detail);
    }
}