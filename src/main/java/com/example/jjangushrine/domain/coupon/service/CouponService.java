package com.example.jjangushrine.domain.coupon.service;

import java.time.LocalDateTime;

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
		validateCouponRequest(createCouponReq);

		Coupon coupon = createCouponReq.toEntity();
		Coupon savedCoupon = couponRepository.save(coupon);

		return CreateCouponRes.fromEntity(savedCoupon);
	}

	@Transactional
	public UpdateCouponRes updateCoupon(Long couponId, UpdateCouponReq request) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new CouponException(ErrorCode.RESOURCE_NOT_FOUND, "쿠폰을 찾을 수 없습니다."));

		validateUpdateRequest(request);
		request.updateEntity(coupon);

		return UpdateCouponRes.fromEntity(coupon);
	}

	private void validateCouponRequest(CreateCouponReq createCouponReq) {
		if (createCouponReq.validFrom().isAfter(createCouponReq.validUntil())) {
			throw new CouponException(ErrorCode.INVALID_DATE_RANGE);
		}

		if (createCouponReq.validFrom().isBefore(LocalDateTime.now())) {
			throw new CouponException(ErrorCode.INVALID_START_DATE);
		}
	}

	public static class UpdateCouponValidator {
		public static void validateUpdateRequest(UpdateCouponReq request) {
			// validFrom과 validUntil이 모두 존재할 때만 검증
			if (request.validFrom() != null && request.validUntil() != null) {
				if (request.validFrom().isAfter(request.validUntil())) {
					throw new CouponException(ErrorCode.INVALID_DATE_RANGE);
				}
			}

			// validFrom이 존재할 때만 현재 시간과 비교
			if (request.validFrom() != null && request.validFrom().isBefore(LocalDateTime.now())) {
				throw new CouponException(ErrorCode.INVALID_START_DATE);
			}
		}
	}

	private void validateUpdateRequest(UpdateCouponReq request) {
		UpdateCouponValidator.validateUpdateRequest(request);
	}
}

