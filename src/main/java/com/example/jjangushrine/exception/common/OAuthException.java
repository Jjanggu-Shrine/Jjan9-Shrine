package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class OAuthException extends BusinessException{
    public OAuthException(ErrorCode errorCode) {
        super(ErrorCode.DUPLICATE_CANCELED_ORDER);
    }
}
