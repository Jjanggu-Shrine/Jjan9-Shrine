package com.example.jjangushrine.domain.coupon.dto.response;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.domain.coupon.entity.Coupon;

public record CreateCouponRes(
	Long couponId,
	String name
) {
	public static CreateCouponRes fromEntity(Coupon coupon) {
		return new CreateCouponRes(
			coupon.getCouponId(),
			coupon.getName()
		);
	}
}
