package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class AccessDeniedException extends BusinessException {
	public AccessDeniedException(ErrorCode errorCode) {
		super(errorCode);
	}

	public AccessDeniedException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
