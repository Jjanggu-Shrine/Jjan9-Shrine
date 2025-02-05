package com.example.jjangushrine.domain.coupon.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.example.jjangushrine.domain.coupon.entity.Coupon;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
	@Query("SELECT c FROM Coupon c WHERE c.couponId = :id")
	Optional<Coupon> findByIdWithLock(Long id);
}
