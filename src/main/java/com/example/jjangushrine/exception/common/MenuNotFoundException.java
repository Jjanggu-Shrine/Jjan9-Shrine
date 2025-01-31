package com.example.jjangushrine.exception.common;

import com.example.jjangushrine.exception.ErrorCode;

public class MenuNotFoundException extends BusinessException {
	public MenuNotFoundException(){
		super(ErrorCode.MENU_NOT_FOUND);
	}

	public MenuNotFoundException(String detail){
		super(ErrorCode.MENU_NOT_FOUND, detail);
	}
}
