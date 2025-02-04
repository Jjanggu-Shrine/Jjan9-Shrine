package com.example.jjangushrine.domain.store.dto;

public class StoreValidationMessage {

    public static final String BUSINESS_NUMBER_BLANK_MESSAGE = "사업자번호를 입력해주세요.";
    public static final String BUSINESS_NUMBER_REG =  "^\\d{3}-\\d{2}-\\d{5}$";
    public static final String BUSINESS_NUMBER_INVALID_MESSAGE = "올바른 사업자번호 형식이 아닙니다.";

    public static final String BUSINESS_NAME_BLANK_MESSAGE = "상호명을 입력해주세요.";
    public static final String BUSINESS_NAME_SIZE_MESSAGE = "상호명은 1자 이상 20자 이하로 입력해주세요.";
    public static final int BUSINESS_NAME_MAX = 20;

    public static final String STORE_NAME_BLANK_MESSAGE = "쇼핑몰 이름을 입력해주세요.";
    public static final String STORE_NAME_SIZE_MESSAGE = "쇼핑몰 이름은 1자 이상 20자 이하로 입력해주세요.";
    public static final int STORE_NAME_MAX = 20;

    public static final String DESCRIPTION_BLANK_MESSAGE = "쇼핑몰 설명을 입력해주세요.";
    public static final String DESCRIPTION_SIZE_MESSAGE = "쇼핑몰 설명은 1자 이상 500자 이하로 입력해주세요.";
    public static final int DESCRIPTION_MAX = 500;

    public static final String BASE_DELIVERY_FEE_BLANK_MESSAGE = "기본 배송비를 입력해주세요.";
    public static final String BASE_DELIVERY_FEE_MIN_MESSAGE = "기본 배송비는 0원 이상이어야 합니다.";
    public static final String BASE_DELIVERY_FEE_MAX_MESSAGE = "기본 배송비는 30,000원을 초과할 수 없습니다.";
    public static final int BASE_DELIVERY_FEE_MAX = 30000;
    public static final int BASE_DELIVERY_FEE_MIN = 0;
}
