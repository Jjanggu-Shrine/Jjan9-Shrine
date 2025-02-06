package com.example.jjangushrine.domain.coupon.dto.request;

import java.time.LocalDateTime;

import com.example.jjangushrine.domain.coupon.dto.CouponValidationMessage;
import com.example.jjangushrine.domain.coupon.entity.Coupon;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCouponReq(
	@NotBlank(message = CouponValidationMessage.COUPON_NAME_BLANK_MESSAGE)
	@Size(max = 20, message = CouponValidationMessage.COUPON_NAME_SIZE_MESSAGE)
	String name,

	@Min(value = 1, message = CouponValidationMessage.DISCOUNT_PERCENT_MIN_MESSAGE)
	@Max(value = 100, message = CouponValidationMessage.DISCOUNT_PERCENT_MAX_MESSAGE)
	Integer discountPercent,

	@Min(value = 0, message = CouponValidationMessage.MIN_ORDER_AMOUNT_MIN_MESSAGE)
	Integer minOrderAmount,

	@NotNull(message = CouponValidationMessage.VALID_FROM_NULL_MESSAGE)
	LocalDateTime validFrom,

	@NotNull(message = CouponValidationMessage.VALID_UNTIL_NULL_MESSAGE)
	LocalDateTime validUntil,

	@Min(value = 1, message = CouponValidationMessage.TOTAL_QUANTITY_MIN_MESSAGE)
	Integer totalQuantity
)  {
	public void updateEntity(Coupon coupon) {
		coupon.update(this);
	}
}
