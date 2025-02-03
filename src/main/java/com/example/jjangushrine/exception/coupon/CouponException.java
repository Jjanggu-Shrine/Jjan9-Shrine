package com.example.jjangushrine.exception.coupon;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.BusinessException;

public class CouponException extends BusinessException {
	public CouponException(ErrorCode errorCode) {
		super(errorCode);
	}

	public CouponException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}