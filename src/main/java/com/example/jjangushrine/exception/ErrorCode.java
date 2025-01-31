package com.example.jjangushrine.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 400 BAD_REQUEST
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "ERR001", "요청값이 올바르지 않습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "ERR002", "요청 데이터 타입이 올바르지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "ERR003", "이미 사용 중인 이메일입니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "ERR004", "이미 사용 중인 사용자명입니다."),
    DUPLICATE_DELETED(HttpStatus.BAD_REQUEST, "ERR005", "이미 삭제 된 사용자입니다."),
    WRONG_CREDENTIALS(HttpStatus.BAD_REQUEST, "ERR006", "인증 정보가 올바르지 않습니다."),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "ERR007", "잘못된 접근입니다."),
    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "ERR008", "잘못된 JSON 형식입니다."),
    INVALID_CONTENT_VALUE(HttpStatus.BAD_REQUEST, "ERR009", "댓글 내용이 올바르지 않습니다."),
    INVALID_CONTENT(HttpStatus.BAD_REQUEST, "ERR010", "가게가 올바르지 않습니다."),
    INVALID_CONTENTS(HttpStatus.BAD_REQUEST, "ERR011", "가게조회가 올바르지 않습니다."),
    INVALID_TOTALAMOUNT(HttpStatus.BAD_REQUEST,"ERR012", "최소 주문 금액을 만족해야합니다."),
    INVALID_ORDER_TIME(HttpStatus.BAD_REQUEST, "ERR013", "지금은 가게 운영 시간이 아닙니다."),
    ORDER_NOT_DELIVERED(HttpStatus.BAD_REQUEST,"ERR014","완료되지 않은 주문입니다."),
    INVALID_USER_ROLE(HttpStatus.BAD_REQUEST,"ERR015","유효하지 않은 UerRole 입니다."),

    // 401 UNAUTHORIZED
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "ERR101", "인증이 필요한 접근입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "ERR102", "로그인에 실패했습니다."),

    // 403 FORBIDDEN
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "ERR201", "접근 권한이 없습니다."),

    // 404 NOT_FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR301", "사용자를 찾을 수 없습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR300", "요청한 리소스를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR303", "댓글을 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR304", "가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR305", "메뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR306", "주문을 찾을 수 없습니다."),


    // 500 INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERR999", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}