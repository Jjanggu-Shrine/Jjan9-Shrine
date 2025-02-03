package com.example.jjangushrine.domain.coupon.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import com.example.jjangushrine.domain.coupon.dto.CouponValidationMessage;
import com.example.jjangushrine.domain.coupon.entity.Coupon;

public record CreateCouponReq(
	@NotBlank(message = CouponValidationMessage.COUPON_NAME_BLANK_MESSAGE)
	@Size(max = CouponValidationMessage.COUPON_NAME_MAX,
		message = CouponValidationMessage.COUPON_NAME_SIZE_MESSAGE)
	String name,

	@NotNull(message = CouponValidationMessage.DISCOUNT_PERCENT_NULL_MESSAGE)
	@Min(value = CouponValidationMessage.DISCOUNT_PERCENT_MIN,
		message = CouponValidationMessage.DISCOUNT_PERCENT_MIN_MESSAGE)
	@Max(value = CouponValidationMessage.DISCOUNT_PERCENT_MAX,
		message = CouponValidationMessage.DISCOUNT_PERCENT_MAX_MESSAGE)
	Integer discountPercent,

	@NotNull(message = CouponValidationMessage.MIN_ORDER_AMOUNT_NULL_MESSAGE)
	@Min(value = CouponValidationMessage.MIN_ORDER_AMOUNT_MIN,
		message = CouponValidationMessage.MIN_ORDER_AMOUNT_MIN_MESSAGE)
		Integer minOrderAmount,

	@NotNull(message = CouponValidationMessage.VALID_FROM_NULL_MESSAGE)
	LocalDateTime validFrom,

	@NotNull(message = CouponValidationMessage.VALID_UNTIL_NULL_MESSAGE)
	LocalDateTime validUntil,

	@NotNull(message = CouponValidationMessage.TOTAL_QUANTITY_NULL_MESSAGE)
	@Min(value = CouponValidationMessage.TOTAL_QUANTITY_MIN,
		message = CouponValidationMessage.TOTAL_QUANTITY_MIN_MESSAGE)
		Integer totalQuantity
) {
	public Coupon toEntity() {
		return Coupon.builder()
			.name(name)
			.discountPercent(discountPercent)
			.minOrderAmount(minOrderAmount)
			.validFrom(validFrom)
			.validUntil(validUntil)
			.totalQuantity(totalQuantity)
			.build();
	}
}
