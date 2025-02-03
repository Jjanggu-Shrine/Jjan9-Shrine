package com.example.jjangushrine.domain.coupon.dto.response;

import com.example.jjangushrine.domain.coupon.entity.Coupon;

public record UpdateCouponRes(
	Long couponId,
	String name
) {
	public static UpdateCouponRes fromEntity(Coupon coupon) {
		return new UpdateCouponRes(
			coupon.getCouponId(),
			coupon.getName()
		);
	}
}
