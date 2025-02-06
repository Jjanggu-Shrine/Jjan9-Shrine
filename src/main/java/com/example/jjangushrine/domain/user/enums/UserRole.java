package com.example.jjangushrine.domain.user.enums;

import java.util.Arrays;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.InvalidException;

public enum UserRole {
	SELLER, USER, ADMIN;

	public static UserRole of(String role) {
		return Arrays.stream(UserRole.values())
			.filter(r -> r.name().equalsIgnoreCase(role))
			.findFirst()
			.orElseThrow(()-> new InvalidException(ErrorCode.INVALID_USER_ROLE));
	}
}
