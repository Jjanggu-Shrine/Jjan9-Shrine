package com.example.jjangushrine.config.security.jwt;

import com.example.jjangushrine.common.ErrorResponse;
import com.example.jjangushrine.config.security.SecurityConst;
import com.example.jjangushrine.config.security.sevice.CustomUserDetailsService;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (isExcludedPath(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerJwt = request.getHeader(SecurityConst.AUTHORIZATION_HEADER);

        if (bearerJwt == null) {
            sendErrorResponse(response, ErrorCode.TOKEN_NOT_FOUND);
            return;
        }

        try {
            authenticateUser(bearerJwt);
            filterChain.doFilter(request, response);

        } catch (SecurityException e) {
            log.error(SecurityConst.TOKEN_VALIDATION_FAIL, e);
            sendErrorResponse(response, ErrorCode.TOKEN_VALIDATION_FAIL);
        } catch (MalformedJwtException e) {
            log.error(SecurityConst.MALFORMED_TOKEN, e);
            sendErrorResponse(response, ErrorCode.MALFORMED_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error(SecurityConst.EXPIRED_TOKEN, e);
            sendErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error(SecurityConst.UNSUPPORTED_TOKEN, e);
            sendErrorResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            log.error(SecurityConst.UNKNOWN_TOKEN_ERROR, e);
            sendErrorResponse(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isExcludedPath(String uri) {
        return uri.startsWith("/api/v1/auth/") ||
                uri.startsWith("/swagger-ui/") ||
                uri.startsWith("/v3/api-docs") ||
                uri.startsWith("/swagger-resources") ||
                uri.startsWith("/auth") ||
                uri.startsWith("/oauth2/") ||
                uri.startsWith("/login/oauth2/");
    }

    private void authenticateUser(String bearerJwt) {
        String jwt = extractToken(bearerJwt);
        Claims claims = jwtUtil.extractClaims(jwt);

        Long id = Long.parseLong(claims.getSubject());
        String email = claims.get("email", String.class);
        UserRole role = UserRole.of(claims.get("role", String.class));

        Authentication authentication =
                CustomUserDetailsService.createAuthentication(id, email, role);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractToken(String bearerToken) {
        if (!StringUtils.hasText(bearerToken) ||
                !bearerToken.startsWith(SecurityConst.BEARER_PREFIX)) {
            throw new MalformedJwtException(SecurityConst.MALFORMED_TOKEN);
        }
        return bearerToken.substring(7);
    }

    private void sendErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        response.setContentType(SecurityConst.CONTENT_TYPE);
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
