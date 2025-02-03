package com.example.jjangushrine.domain.coupon.service;

import java.time.LocalDateTime;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.domain.coupon.dto.ValidatableCouponDate;
import com.example.jjangushrine.domain.coupon.dto.request.CreateCouponReq;
import com.example.jjangushrine.domain.coupon.dto.request.UpdateCouponReq;
import com.example.jjangushrine.domain.coupon.dto.response.CreateCouponRes;
import com.example.jjangushrine.domain.coupon.dto.response.UpdateCouponRes;
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
		validateCouponDate(createCouponReq);

		Coupon coupon = createCouponReq.toEntity();
		Coupon savedCoupon = couponRepository.save(coupon);

		return CreateCouponRes.fromEntity(savedCoupon);
	}

	@Transactional
	public UpdateCouponRes updateCoupon(Long couponId, UpdateCouponReq updateCouponReq) {
		Coupon coupon = couponRepository.findById(couponId)
				.orElseThrow(() -> new CouponException(ErrorCode.COUPON_NOT_FOUND));

		validateCouponDate(updateCouponReq);

		updateCouponReq.updateEntity(coupon);

		return UpdateCouponRes.fromEntity(coupon);
	}

	private void validateCouponDate(ValidatableCouponDate validatableCouponDate) {
			if (validatableCouponDate.validFrom().isAfter(validatableCouponDate.validUntil())) {
				throw new CouponException(ErrorCode.INVALID_DATE_RANGE);
			}

			if (validatableCouponDate.validFrom().isBefore(LocalDateTime.now())) {
				throw new CouponException(ErrorCode.INVALID_START_DATE);
			}
		}
	}
