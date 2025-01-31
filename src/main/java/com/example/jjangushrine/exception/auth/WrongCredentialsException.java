package com.example.jjangushrine.exception.auth;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class WrongCredentialsException extends BusinessException {
    public WrongCredentialsException() {
        super(ErrorCode.WRONG_CREDENTIALS);
    }

    public WrongCredentialsException(String detail) {
        super(ErrorCode.WRONG_CREDENTIALS, detail);
    }
}