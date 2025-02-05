package com.example.jjangushrine.domain.coupon.dto.response;

import java.time.LocalDateTime;

import com.example.jjangushrine.domain.coupon.entity.UserCoupon;

public record UserCouponRes(
	Long userCouponId,
	String couponName,
	Integer discountPercent,
	Integer minOrderAmount,
	LocalDateTime validUntil
) {
	public static UserCouponRes from(UserCoupon userCoupon) {
		return new UserCouponRes(
			userCoupon.getUserCouponId(),
			userCoupon.getCoupon().getName(),
			userCoupon.getCoupon().getDiscountPercent(),
			userCoupon.getCoupon().getMinOrderAmount(),
			userCoupon.getCoupon().getValidUntil()
		);
	}
}
