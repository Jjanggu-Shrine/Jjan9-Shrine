package com.example.jjangushrine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.jjangushrine.common.ErrorResponse;
import com.example.jjangushrine.exception.common.BusinessException;
import com.fasterxml.jackson.core.JsonParseException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    // AccessDeniedException 처리 추가
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
//        log.error("AccessDeniedException", e);
//        return ResponseEntity
//            .status(HttpStatus.FORBIDDEN)
//            .body(ErrorResponse.of(ErrorCode.FORBIDDEN_ACCESS, "접근 권한이 없습니다."));
//    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("BusinessException", e);
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    // MethodArgumentNotValidException은 Spring Framework에서 제공하는 예외 클래스입니다.
    // 주로 @Valid 또는 @Validated 어노테이션을 사용한 요청 데이터 검증이 실패했을 때
    // 발생하는 예외입니다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        String detail = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, detail));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error("JSON 파싱 에러", e);
        String detail = "올바른 JSON 형식이 아닙니다. 요청 데이터를 확인해주세요.";

        // JsonParseException이 원인인 경우 더 자세한 메시지 제공
        if (e.getCause() instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) e.getCause();
            detail = String.format("JSON 파싱 오류 (위치: %d 행, %d 열): %s",
                    jpe.getLocation().getLineNr(),
                    jpe.getLocation().getColumnNr(),
                    jpe.getOriginalMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ErrorCode.INVALID_JSON_FORMAT, detail));
    }
}