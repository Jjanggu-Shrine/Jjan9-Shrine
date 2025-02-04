package com.example.jjangushrine.domain.coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jjangushrine.domain.coupon.dto.request.CouponIssueReq;
import com.example.jjangushrine.domain.coupon.dto.response.UserCouponRes;
import com.example.jjangushrine.domain.coupon.entity.Coupon;
import com.example.jjangushrine.domain.coupon.entity.UserCoupon;
import com.example.jjangushrine.domain.coupon.repository.CouponRepository;
import com.example.jjangushrine.domain.coupon.repository.UserCouponRepository;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CouponIssueService {
	private final CouponRepository couponRepository;
	private final UserCouponRepository userCouponRepository;
	private final LockService lockService;
	private final UserRepository userRepository;  // 추가

	public UserCouponRes issueCoupon(CouponIssueReq request) {
		log.info("Coupon issue request - userId: {}, couponId: {}",
			request.userId(), request.couponId());

		String lockKey = "coupon:" + request.couponId();

		return lockService.executeWithLock(lockKey, 3000, 1000, () -> {
			// 1. 쿠폰 조회 및 유효성 검증
			Coupon coupon = findCouponById(request.couponId());

			// 2. 쿠폰 수량 검증
			validateCouponQuantity(coupon);

			// 3. 사용자 중복 발급 검증
			validateUserCouponDuplicate(request.userId(), request.couponId());

			// 4. 쿠폰 발급 및 수량 감소
			coupon.decreaseQuantity();
			UserCoupon userCoupon = createUserCoupon(request.userId(), coupon);

			log.info("Coupon issued successfully - userCouponId: {}",
				userCoupon.getUserCouponId());

			return UserCouponRes.from(userCoupon);
		});
	}

	private Coupon findCouponById(Long couponId) {
		return couponRepository.findById(couponId)
			.orElseThrow(() -> {
				log.error("Coupon not found - couponId: {}", couponId);
				return new EntityNotFoundException("존재하지 않는 쿠폰입니다.");
			});
	}

	private void validateCouponQuantity(Coupon coupon) {
		if (coupon.getUsedQuantity() >= coupon.getTotalQuantity()) {
			log.warn("Coupon sold out - couponId: {}", coupon.getCouponId());
			throw new IllegalStateException("쿠폰이 모두 소진되었습니다.");
		}
	}

	private void validateUserCouponDuplicate(Long userId, Long couponId) {
		if (userCouponRepository.existsByUserIdAndCouponId(userId, couponId)) {
			log.warn("Duplicate coupon issue attempt - userId: {}, couponId: {}",
				userId, couponId);
			throw new IllegalStateException("이미 발급받은 쿠폰입니다.");
		}
	}

	private UserCoupon createUserCoupon(Long userId, Coupon coupon) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> {
				log.error("User not found - userId: {}", userId);
				return new EntityNotFoundException("사용자를 찾을 수 없습니다.");
			});

		return userCouponRepository.save(
			UserCoupon.builder()
				.user(user)
				.coupon(coupon)
				.build()
		);
	}
}
