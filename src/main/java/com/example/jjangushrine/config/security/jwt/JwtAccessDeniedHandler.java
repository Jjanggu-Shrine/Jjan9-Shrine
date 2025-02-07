package com.example.jjangushrine.config.security.jwt;

import com.example.jjangushrine.common.ErrorResponse;
import com.example.jjangushrine.config.security.SecurityConst;
import com.example.jjangushrine.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn(SecurityConst.AUTH_ERROR_LOG,
                accessDeniedException.getClass().getSimpleName(),
                accessDeniedException.getMessage()
        );

        ErrorCode errorCode = ErrorCode.FORBIDDEN_ACCESS;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        response.setContentType(SecurityConst.CONTENT_TYPE);
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
