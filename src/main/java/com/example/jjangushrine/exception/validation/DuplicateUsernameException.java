package com.example.jjangushrine.exception.validation;


import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

import lombok.Getter;

@Getter
public class DuplicateUsernameException extends BusinessException {
    public DuplicateUsernameException() {
        super(ErrorCode.DUPLICATE_USERNAME);
    }

    public DuplicateUsernameException(String detail) {
        super(ErrorCode.DUPLICATE_USERNAME, detail);
    }
}