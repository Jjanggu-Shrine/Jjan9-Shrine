package com.example.jjangushrine.domain.coupon.dto;

public class CouponValidationMessage {
    public static final String COUPON_NAME_BLANK_MESSAGE = "쿠폰 이름은 필수입니다";
    public static final String COUPON_NAME_SIZE_MESSAGE = "쿠폰 이름은 20자를 초과할 수 없습니다";
    public static final int COUPON_NAME_MAX = 20;

    public static final String DISCOUNT_PERCENT_NULL_MESSAGE = "할인율은 필수입니다";
    public static final String DISCOUNT_PERCENT_MIN_MESSAGE = "할인율은 1 이상이어야 합니다";
    public static final String DISCOUNT_PERCENT_MAX_MESSAGE = "할인율은 100 이하여야 합니다";
    public static final int DISCOUNT_PERCENT_MIN = 1;
    public static final int DISCOUNT_PERCENT_MAX = 100;

    public static final String MIN_ORDER_AMOUNT_NULL_MESSAGE = "최소 주문 금액은 필수입니다";
    public static final String MIN_ORDER_AMOUNT_MIN_MESSAGE = "최소 주문 금액은 0 이상이어야 합니다";
    public static final int MIN_ORDER_AMOUNT_MIN = 0;

    public static final String VALID_FROM_NULL_MESSAGE = "쿠폰 시작일은 필수입니다";
    public static final String VALID_UNTIL_NULL_MESSAGE = "쿠폰 종료일은 필수입니다";

    public static final String TOTAL_QUANTITY_NULL_MESSAGE = "쿠폰 수량은 필수입니다";
    public static final String TOTAL_QUANTITY_MIN_MESSAGE = "쿠폰 수량은 1개 이상이어야 합니다";
    public static final int TOTAL_QUANTITY_MIN = 1;
}