package com.example.jjangushrine.domain.coupon.entity;

import java.time.LocalDateTime;

import com.example.jjangushrine.common.BaseEntity;
import com.example.jjangushrine.domain.user.entity.User;

import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.coupon.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_coupons")
public class UserCoupon extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userCouponId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id", nullable = false)
	private Coupon coupon;

	@Column(nullable = false)
	private boolean isUsed;

	private LocalDateTime usedAt;

	@Builder
	public UserCoupon(User user, Coupon coupon) {
		this.user = user;
		this.coupon = coupon;
		this.isUsed = false;
	}

	public void markAsUsed() {
		if (this.usedAt != null) {
			throw new CouponException(ErrorCode.DUPLICATE_USED_COUPON);
		}
		this.usedAt = LocalDateTime.now();
	}

	public void unmarkAsUsed() {
		this.usedAt = null;
	}
}
