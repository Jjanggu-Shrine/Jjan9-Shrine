package com.example.jjangushrine.domain.coupon.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jjangushrine.domain.coupon.entity.UserCoupon;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
	@Query("SELECT CASE WHEN COUNT(uc) > 0 THEN true ELSE false END FROM UserCoupon uc WHERE uc.user.id = :userId AND uc.coupon.couponId = :couponId")
	boolean existsByUserIdAndCouponId(Long userId, Long couponId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT uc FROM UserCoupon uc WHERE uc.user.id = :userId AND uc.coupon.couponId= :couponId AND uc.usedAt IS NULL")
	Optional<UserCoupon> findByUserAndCouponWithLock(@Param("userId") Long userId,@Param("couponId") Long couponId);

	Optional<UserCoupon> findByUser_IdAndCoupon_CouponId(Long id, Long couponId);

	@Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon WHERE uc.user.id = :userId ORDER BY uc.createdAt DESC")
	List<UserCoupon> findAllByUserIdWithCoupon(Long userId);
}
