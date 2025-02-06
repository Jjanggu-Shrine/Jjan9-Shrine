package com.example.jjangushrine.domain.oauth2.handler;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.SecurityConst;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.config.security.jwt.JwtUtil;
import com.example.jjangushrine.domain.oauth2.model.ProviderUser;
import com.example.jjangushrine.domain.oauth2.test.DefaultCustomOAuth2User;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        DefaultCustomOAuth2User defaultCustomOAuth2User = (DefaultCustomOAuth2User) authentication.getPrincipal();

        ProviderUser providerUser = defaultCustomOAuth2User.getProviderUser();

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .email(providerUser.getEmail())
                .id(providerUser.getId())
                .role(UserRole.USER)
                .build();

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        String token = jwtUtil.generateToken(newAuth);
        sendSuccessResponse(response, token);
    }

    private void sendSuccessResponse(HttpServletResponse response, String token) throws IOException {
        response.setContentType(SecurityConst.CONTENT_TYPE);
        response.setHeader(SecurityConst.AUTHORIZATION_HEADER,
                SecurityConst.BEARER_PREFIX + token);

        ApiResponse<String> apiResponse = ApiResponse.success(
                ApiResMessage.OAUTH_LOGIN_SUCCESS,
                token
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}