package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class InvalidException extends BusinessException {
	public InvalidException(ErrorCode errorCode){
		super(errorCode);
	}

	public InvalidException(ErrorCode errorCode, String detail){
		super(errorCode, detail);
	}
}
