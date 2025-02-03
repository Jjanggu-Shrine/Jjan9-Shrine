package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

// Store 에 해당 Exception 추가 시 삭제 예정
public class StoreAccessDeniedException extends BusinessException {
	public StoreAccessDeniedException() {
		super(ErrorCode.STORE_FORBIDDEN_ACCESS);
	}
}
