package com.example.jjangushrine.exception.validation;


import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }

    public DuplicateEmailException(String detail) {
        super(ErrorCode.DUPLICATE_EMAIL, detail);
    }
}