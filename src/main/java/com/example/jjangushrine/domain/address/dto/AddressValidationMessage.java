package com.example.jjangushrine.domain.address.dto;

public class AddressValidationMessage {

    public static final String RECIPIENT_NAME_BLANK_MESSAGE = "수령인 이름을 입력해주세요.";
    public static final int RECIPIENT_NAME_MIN = 2;
    public static final int RECIPIENT_NAME_MAX = 20;
    public static final String RECIPIENT_NAME_LENGTH_MESSAGE = "수령인 이름은 2자 이상 20자 이하로 입력해주세요.";

    public static final String ADDRESS_NAME_BLANK_MESSAGE = "주소 별칭을 입력해주세요.";
    public static final int ADDRESS_NAME_MAX = 20;
    public static final String ADDRESS_NAME_LENGTH_MESSAGE = "주소 별칭은 20자 이내로 입력해주세요.";

    public static final String ADDRESS_BLANK_MESSAGE = "주소를 입력해 주세요.";
    public static final String ADDRESS_LENGTH_MESSAGE = "주소는 100자 이내로 입력해 주세요.";
    public static final int ADDRESS_MAX = 100;

    public static final String ADDRESS_DETAIL_BLANK_MESSAGE = "상세주소를 입력해 주세요.";
    public static final String ADDRESS_DETAIL_LENGTH_MESSAGE = "상세주소는 100자 이내로 입력해 주세요.";
    public static final int ADDRESS_DETAIL_MAX = 100;

    public static final String ZIP_CODE_BLANK_MESSAGE = "우편번호를 입력해 주세요.";
    public static final String ZIP_CODE_REG = "^\\d{5}$";
    public static final String INVALID_ZIP_CODE_MESSAGE = "우편번호는 5자리 숫자로 입력해 주세요.";
}

