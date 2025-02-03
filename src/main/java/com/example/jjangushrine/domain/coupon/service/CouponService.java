package com.example.jjangushrine.domain.coupon.service;

import java.time.LocalDateTime;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.domain.coupon.dto.request.CreateCouponReq;
import com.example.jjangushrine.domain.coupon.dto.response.CreateCouponRes;
import com.example.jjangushrine.domain.coupon.entity.Coupon;
import com.example.jjangushrine.domain.coupon.repository.CouponRepository;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.coupon.CouponException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	@Transactional
	public CreateCouponRes createCoupon(CreateCouponReq createCouponReq) {
		validateCouponRequest(createCouponReq);

		Coupon coupon = createCouponReq.toEntity();
		Coupon savedCoupon = couponRepository.save(coupon);

		return CreateCouponRes.fromEntity(savedCoupon);
	}

	private void validateCouponRequest(CreateCouponReq createCouponReq) {
			if (createCouponReq.validFrom().isAfter(createCouponReq.validUntil())) {
				throw new CouponException(ErrorCode.INVALID_DATE_RANGE);
			}

			if (createCouponReq.validFrom().isBefore(LocalDateTime.now())) {
				throw new CouponException(ErrorCode.INVALID_START_DATE);
			}
		}
	}
