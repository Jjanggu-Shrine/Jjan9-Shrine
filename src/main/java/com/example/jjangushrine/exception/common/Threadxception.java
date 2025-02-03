package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class Threadxception extends BusinessException {
    public Threadxception(String message) {
        super(ErrorCode.INVALID_JSON_PROCESSING, message);
    }
}
