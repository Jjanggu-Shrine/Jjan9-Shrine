package com.example.jjangushrine.domain.coupon.dto.response;

import java.time.LocalDateTime;

import com.example.jjangushrine.domain.coupon.entity.UserCoupon;

public record UserCouponListRes(
	Long userCouponId,
	String couponName,
	Integer discountPercent,
	Integer minOrderAmount,
	LocalDateTime validFrom,
	LocalDateTime validUntil,
	boolean isUsed,
	LocalDateTime usedAt
) {
	public static UserCouponListRes fromEntity(UserCoupon userCoupon) {
		return new UserCouponListRes(
			userCoupon.getUserCouponId(),
			userCoupon.getCoupon().getName(),
			userCoupon.getCoupon().getDiscountPercent(),
			userCoupon.getCoupon().getMinOrderAmount(),
			userCoupon.getCoupon().getValidFrom(),
			userCoupon.getCoupon().getValidUntil(),
			userCoupon.isUsed(),
			userCoupon.getUsedAt()
		);
	}
}
