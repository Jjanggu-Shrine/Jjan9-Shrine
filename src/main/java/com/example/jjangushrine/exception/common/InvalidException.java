package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class InvalidException extends BusinessException {
	public InvalidException(){
		super(ErrorCode.INVALID_USER_ROLE);
	}

	public InvalidException(String detail){
		super(ErrorCode.INVALID_USER_ROLE, detail);
	}
}
