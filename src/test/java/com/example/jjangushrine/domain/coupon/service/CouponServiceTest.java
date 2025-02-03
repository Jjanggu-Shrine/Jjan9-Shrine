package com.example.jjangushrine.domain.coupon.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.jjangushrine.domain.coupon.dto.request.CreateCouponReq;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.coupon.CouponException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {
	@InjectMocks
	private CouponService couponService;


	@Test
	@DisplayName("쿠폰 생성 시 validFrom이 현재 시간보다 이전이면 예외가 생긴다.")
	void createCoupon_현재_시간보다_이전() {
		//given
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime validFrom = now.minusDays(1);
		LocalDateTime validTo = now.plusDays(1);

		CreateCouponReq request = new CreateCouponReq(
			"테스트 쿠폰",
			30,
			10000,
			validFrom,
			validTo,
			100
		);

	// when & then
	assertThatThrownBy(() -> couponService.createCoupon(request))
		.isInstanceOf(CouponException .class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_START_DATE);
	}

	@Test
	@DisplayName("쿠폰 생성 시 validUntil이 validFrom보다 이전이면 예외가 생긴다.")
	void createCoupon_validUntil_validFrom_이전() {
		//given
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime validFrom = now.plusDays(2);
		LocalDateTime validTo = now.plusDays(1); // validFrom보다 이전 날짜

		CreateCouponReq request = new CreateCouponReq(
			"테스트 쿠폰",
			30,
			10000,
			validFrom,
			validTo,
			100
		);

		// when & then
		assertThatThrownBy(() -> couponService.createCoupon(request))
			.isInstanceOf(CouponException .class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_DATE_RANGE);
	}
	@Test
	@DisplayName("쿠폰 생성 시 잘못된 날짜 형식이면 예외가 발생한다")
	void createCoupon_잘못된_날짜형식() {
		// given
		String invalidDateString = "2025-02-04"; // T00:00:00 없는 형식

		assertThatThrownBy(() -> {
			LocalDateTime.parse(invalidDateString);
		}).isInstanceOf(java.time.format.DateTimeParseException.class);
	}
}