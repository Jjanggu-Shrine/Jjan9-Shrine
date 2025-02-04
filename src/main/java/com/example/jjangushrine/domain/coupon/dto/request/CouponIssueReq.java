package com.example.jjangushrine.domain.coupon.dto.request;

public record CouponIssueReq(
	Long userId,
	Long couponId
) {
	// Validation을 위한 정적 팩토리 메서드
	public static CouponIssueReq of(Long userId, Long couponId) {
		return new CouponIssueReq(userId, couponId);
	}
}
