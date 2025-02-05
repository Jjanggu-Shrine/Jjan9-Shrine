package com.example.jjangushrine.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 400 BAD_REQUEST
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "ERR001", "요청값이 올바르지 않습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "ERR002", "요청 데이터 타입이 올바르지 않습니다."),
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
    INVALID_PRINCIPAL_TYPE(HttpStatus.BAD_REQUEST, "ERR016", "지원하지 않는 사용자 타입입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,"ERR017", "지원되지 않는 JWT 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "ERR018", "JWT 토큰이 없습니다."),

	//Coupon
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, "ERR019", "유효기간의 시작일이 종료일보다 늦을 수 없습니다."),
    INVALID_START_DATE(HttpStatus.BAD_REQUEST, "ERR020", "쿠폰 시작일은 현재 시점 이후여야 합니다."),
    INVALID_COUPON_REQUEST(HttpStatus.BAD_REQUEST, "ERR021", "쿠폰 발급 요청이 유효하지 않습니다."),

    INVALID_JSON_PROCESSING(HttpStatus.BAD_REQUEST, "ERR019", "JSON 직렬화 역직렬화 중 오류가 발생했습니다"),


    // 401 UNAUTHORIZED
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "ERR101", "로그인이 필요합니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "ERR102", "잘못된 아이디 또는 비밀번호입니다."),
    TOKEN_VALIDATION_FAIL(HttpStatus.UNAUTHORIZED,"ERR103", "JWT 보안 검증에 실패했습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED,"ERR104", "잘못된 JWT 형식입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"ERR105", "만료된 JWT 토큰입니다."),

    // 403 FORBIDDEN
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "ERR201", "접근 권한이 없습니다."),

    //Coupon
    ADMIN_ACCESS_DENIED(HttpStatus.FORBIDDEN, "ERR202", "관리자 권한이 필요한 작업입니다."),
	STORE_FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "ERR203", "해당 상점에 접근할 권한이 없습니다."),
    PRODUCT_FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "ERR204", "해당 상품에 접근할 수 없습니다."),

    // 404 NOT_FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR301", "사용자를 찾을 수 없습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR300", "요청한 리소스를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR303", "댓글을 찾을 수 없습니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR304", "가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR305", "메뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR306", "주문을 찾을 수 없습니다."),
    SELLER_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR307", "판매자를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR308", "상품을 찾을 수 없습니다."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR308", "해당 배송지를 찾을 수 없습니다."),
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR308", "존재하지 않는 쿠폰입니다."),
    USER_COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR310", "발급된 쿠폰을 찾을 수 없습니다."),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "ERR309", "해당 장바구니를 찾을 수 없습니다."),

    // 409 CONFLICT
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "ERR401", "이미 사용 중인 이메일입니다."),
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "ERR402", "이미 사용 중인 사용자명입니다."),
    DUPLICATE_LOCK(HttpStatus.CONFLICT, "ERR403", "다른 요청이 처리중입니다."),
    DUPLICATE_DEFAULT(HttpStatus.CONFLICT, "ERR404", "이미 기본값으로 설정된 주소입니다."),
    DUPLICATE_STORE(HttpStatus.CONFLICT, "ERR405", "이미 등록된 쇼핑몰이 있습니다."),
    DUPLICATE_STORE_DELETE(HttpStatus.CONFLICT, "ERR406", "이미 삭제된 쇼핑몰입니다."),
    DUPLICATE_BUSINESS_NUMBER(HttpStatus.CONFLICT, "ERR407", "이미 등록된 사업자 번호입니다."),
    DUPLICATE_ADDRESS_DELETE(HttpStatus.CONFLICT, "ERR408", "이미 삭제된 배송지입니다."),
    DUPLICATE_USER_DELETE(HttpStatus.CONFLICT, "ERR409", "이지 탈퇴한 유저입니다."),
    DUPLICATE_SELLER_DELETE(HttpStatus.CONFLICT, "ERR410", "이미 탈퇴한 판매자입니다."),
    DUPLICATE_USED_COUPON(HttpStatus.CONFLICT, "ERR411", "이미 사용된 쿠폰입니다."),
    DUPLICATE_OUT_OF_STOCK(HttpStatus.CONFLICT, "ERR412", "재고가 소진되었습니다."),
    DUPLICATE_CANCELED_ORDER(HttpStatus.CONFLICT, "ERR413", "이미 주문취소가 되었습니다."),



    //coupon
    COUPON_SOLD_OUT(HttpStatus.CONFLICT, "ERR411", "쿠폰이 모두 소진되었습니다."),
    COUPON_ALREADY_USED(HttpStatus.CONFLICT, "ERR412", "이미 사용된 쿠폰입니다."),
    DUPLICATE_COUPON_ISSUE(HttpStatus.CONFLICT, "ERR413", "이미 발급받은 쿠폰입니다."),

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