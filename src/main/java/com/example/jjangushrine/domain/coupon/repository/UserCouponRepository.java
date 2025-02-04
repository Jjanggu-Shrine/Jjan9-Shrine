package com.example.jjangushrine.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jjangushrine.domain.coupon.entity.UserCoupon;

import java.util.Optional;


@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
	@Query("SELECT CASE WHEN COUNT(uc) > 0 THEN true ELSE false END FROM UserCoupon uc WHERE uc.user.id = :userId AND uc.coupon.couponId = :couponId")
	boolean existsByUserIdAndCouponId(Long userId, Long couponId);

	Optional<UserCoupon> findByUser_IdAndCoupon_CouponIdAndUsedAtIsNull(Long userId, Long couponId);
}
