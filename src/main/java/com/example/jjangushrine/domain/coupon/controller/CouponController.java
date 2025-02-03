package com.example.jjangushrine.domain.coupon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.domain.coupon.dto.request.CreateCouponReq;
import com.example.jjangushrine.domain.coupon.dto.request.UpdateCouponReq;
import com.example.jjangushrine.domain.coupon.dto.response.CreateCouponRes;
import com.example.jjangushrine.domain.coupon.dto.response.UpdateCouponRes;
import com.example.jjangushrine.domain.coupon.service.CouponService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/coupons")
@RequiredArgsConstructor
public class CouponController {

	private final CouponService couponService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")  // 어드민 권한 체크
	public ApiResponse<CreateCouponRes> creatCoupon(@Valid @RequestBody CreateCouponReq req) {
		CreateCouponRes response = couponService.createCoupon(req);
		return ApiResponse.success(ApiResMessage.COUPON_CREATE_SUCCESS, response);
	}

	@PatchMapping("/{couponId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<UpdateCouponRes> updateCoupon(
		@PathVariable Long couponId,
		@Valid @RequestBody UpdateCouponReq request
	) {
		UpdateCouponRes response = couponService.updateCoupon(couponId, request);
		return ApiResponse.success("쿠폰이 성공적으로 수정되었습니다.", response);
	}

	@DeleteMapping("/{couponId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<Void> deleteCoupon(@PathVariable Long couponId) {
		couponService.deleteCoupon(couponId);
		return ApiResponse.success("쿠폰이 성공적으로 삭제되었습니다.");
	}
}
