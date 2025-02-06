package com.example.jjangushrine.domain.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jjangushrine.domain.coupon.dto.response.UserCouponListRes;
import com.example.jjangushrine.domain.coupon.repository.UserCouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCouponService {

	private final UserCouponRepository userCouponRepository;

	public List<UserCouponListRes> listUserCoupons(Long userId) {
		return userCouponRepository.findAllByUserIdWithCoupon(userId)
			.stream()
			.map(UserCouponListRes::fromEntity)
			.toList();
	}
}
