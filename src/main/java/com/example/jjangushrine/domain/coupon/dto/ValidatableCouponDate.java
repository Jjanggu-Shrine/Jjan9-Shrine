package com.example.jjangushrine.domain.coupon.dto;

import java.time.LocalDateTime;

public interface ValidatableCouponDate {
	LocalDateTime validFrom();
	LocalDateTime validUntil();
}
