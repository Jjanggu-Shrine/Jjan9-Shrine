package com.example.jjangushrine.domain.coupon.dto.response;

import java.time.LocalDateTime;

import com.example.jjangushrine.domain.coupon.entity.Coupon;

public record UpdateCouponRes(
	Long couponId,
	String name,
	Integer discountPercent,
	Integer minOrderAmount,
	LocalDateTime validFrom,
	LocalDateTime validUntil,
	Integer totalQuantity
) {
	public static UpdateCouponRes fromEntity(Coupon coupon) {
		return new UpdateCouponRes(
			coupon.getCouponId(),
			coupon.getName(),
			coupon.getDiscountPercent(),
			coupon.getMinOrderAmount(),
			coupon.getValidFrom(),
			coupon.getValidUntil(),
			coupon.getTotalQuantity()
		);
	}
}

