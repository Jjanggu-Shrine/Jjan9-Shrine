package com.example.jjangushrine.domain.coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.common.ErrorResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.coupon.dto.request.CouponIssueReq;
import com.example.jjangushrine.domain.coupon.dto.response.UserCouponListRes;
import com.example.jjangushrine.domain.coupon.dto.response.UserCouponRes;
import com.example.jjangushrine.domain.coupon.service.CouponIssueService;
import com.example.jjangushrine.domain.coupon.service.UserCouponService;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/coupons")
@RequiredArgsConstructor
public class UserCouponController {

	private final CouponIssueService couponIssueService;
	private final UserCouponService userCouponService;

	@PostMapping("/{couponId}/issue")
	public ResponseEntity<?> issueCoupon(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long couponId
	) {
		log.info("Coupon issue request - userId: {}, couponId: {}", userDetails.getId(), couponId);

		try {
			CouponIssueReq request = CouponIssueReq.of(userDetails.getId(), couponId);
			UserCouponRes response = couponIssueService.issueCoupon(request);

			log.info("Coupon issued successfully - userCouponId: {}", response.userCouponId());
			return ResponseEntity.ok(ApiResponse.success(ApiResMessage.COUPON_USER_SUCCESS, response));

		} catch (IllegalStateException e) {
			log.warn("Coupon issue failed - userId: {}, couponId: {}, reason: {}",
				userDetails.getId(), couponId, e.getMessage());
			return ResponseEntity.badRequest()
				.body(ErrorResponse.of(ErrorCode.INVALID_COUPON_REQUEST, e.getMessage()));

		} catch (Exception e) {
			log.error("Unexpected error during coupon issue - userId: {}, couponId: {}",
				userDetails.getId(), couponId, e);
			return ResponseEntity.internalServerError()
				.body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
		}
	}


	@GetMapping
	public ApiResponse<List<UserCouponListRes>> listUserCoupons(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		List<UserCouponListRes> coupons = userCouponService.listUserCoupons(userDetails.getId());
		return ApiResponse.success(ApiResMessage.COUPON_LIST_SUCCESS, coupons);
	}
}
