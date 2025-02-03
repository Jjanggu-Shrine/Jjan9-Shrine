package com.example.jjangushrine.domain.user.dto;

public class UserValidationMessage {
    public static final String EMAIL_BLANK_MESSAGE = "이메일 주소를 입력해 주세요.";
    public static final String EMAIL_REG = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]$";
    public static final String INVALID_EMAIL_MESSAGE = "올바른 이메일 형식으로 입력해 주세요.";

    public static final String PASSWORD_BLANK_MESSAGE = "비밀번호를 입력해 주세요.";
    public static final String PASSWORD_REG = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+])[a-zA-Z\\d!@#$%^&*()\\-_=+]*$";
    public static final String INVALID_PASSWORD_MESSAGE = "비밀번호엔 대소문자 영문, 숫자, 특수문자를 최소 1글자씩 포함해 주세요.";
    public static final String PASSWORD_MIN_MESSAGE = "비밀번호는 8자 이상 입력해 주세요.";
    public static final int PASSWORD_MIN = 8;

    public static final String NICKNAME_RANGE_MESSAGE = "닉네임은 2자 이상 15자 이하로 입력해 주세요.";
    public static final String NICKNAME_BLANK_MESSAGE = "닉네임을 입력해 주세요.";
    public static final int NICKNAME_MAX = 15;
    public static final int NICKNAME_MIN = 2;

    public static final String PHONE_NUMBER_BLANK_MESSAGE = "전화번호를 입력해 주세요.";
    public static final String PHONE_NUMBER_REG = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "올바른 전화번호 형식으로 입력해 주세요.";

    public static final String REPR_NAME_BLANK_MESSAGE = "대표자명을 입력해 주세요.";
    public static final String REPR_NAME_REG = "^[가-힣a-zA-Z\\s]+$";  // 길이 제한 제거
    public static final String INVALID_REPR_NAME_MESSAGE = "대표자명은 한글 또는 영문만 입력 가능합니다.";
    public static final String REPR_NAME_SIZE_MESSAGE = "대표자명은 2~20자 이내로 입력해 주세요.";
    public static final int REPR_NAME_MIN = 2;
    public static final int REPR_NAME_MAX = 20;
}
