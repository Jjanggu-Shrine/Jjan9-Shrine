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
    public static final String PHONE_NUMBER_REG = "^(\\d{2,4}-\\d{3,4}-\\d{4}|\\d{9,16})$";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "올바른 전화번호 형식으로 입력해 주세요.";

    public static final String USER_ROLE_BLANK_MESSAGE = "유저권한을 입력해 주세요.";
    public static final String INVALID_USER_ROLE = "유효하지 않은 유저권한입니다.";
}
