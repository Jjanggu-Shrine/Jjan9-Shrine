package com.example.jjangushrine.domain.coupon.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.jjangushrine.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.jjangushrine.domain.coupon.dto.request.UpdateCouponReq;
import com.example.jjangushrine.domain.coupon.enums.CouponStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupons")
public class Coupon extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponId;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(nullable = false)
	private Integer discountPercent;

	@Column(nullable = false)
	private Integer minOrderAmount;

	@Column(nullable = false)
	private LocalDateTime validFrom;

	@Column(nullable = false)
	private LocalDateTime validUntil;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CouponStatus status;

	@Column(nullable = false)
	private Integer totalQuantity;

	@Column(nullable = false)
	private Integer usedQuantity;

	@Builder
	public Coupon(String name, Integer discountPercent, Integer minOrderAmount,
		LocalDateTime validFrom, LocalDateTime validUntil, Integer totalQuantity) {
		this.name = name;
		this.discountPercent = discountPercent;
		this.minOrderAmount = minOrderAmount;
		this.validFrom = validFrom;
		this.validUntil = validUntil;
		this.status = CouponStatus.AVAILABLE;
		this.totalQuantity = totalQuantity;
		this.usedQuantity = 0;
	}

	public void update(UpdateCouponReq req) {
		Optional.ofNullable(req.name()).ifPresent(value -> this.name = value);
		Optional.ofNullable(req.discountPercent()).ifPresent(value -> this.discountPercent = value);
		Optional.ofNullable(req.minOrderAmount()).ifPresent(value -> this.minOrderAmount = value);
		Optional.ofNullable(req.validFrom()).ifPresent(value -> this.validFrom = value);
		Optional.ofNullable(req.validUntil()).ifPresent(value -> this.validUntil = value);
		Optional.ofNullable(req.totalQuantity()).ifPresent(value -> this.totalQuantity = value);
	}
}
