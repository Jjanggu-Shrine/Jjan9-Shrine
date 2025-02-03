package com.example.jjangushrine.domain.coupon.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jjangushrine.domain.coupon.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
