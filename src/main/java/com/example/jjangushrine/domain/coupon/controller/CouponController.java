package com.example.jjangushrine.domain.coupon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.domain.coupon.dto.request.CreateCouponReq;
import com.example.jjangushrine.domain.coupon.dto.response.CreateCouponRes;
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
}
