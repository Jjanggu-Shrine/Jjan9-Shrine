package com.example.jjangushrine.domain.oauth2.service;

import com.example.jjangushrine.domain.oauth2.model.GoogleUser;
import com.example.jjangushrine.domain.oauth2.model.NaverUser;
import com.example.jjangushrine.domain.oauth2.model.ProviderUser;
import com.example.jjangushrine.domain.oauth2.test.DefaultCustomOAuth2User;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.OAuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        ProviderUser providerUser = getProviderUser(oAuth2User, userRequest.getClientRegistration());

        log.info("providerUser : {}", providerUser.toString());
        User user = findOrCreateUser(providerUser);
        providerUser.setId(user.getId());
        log.info("OAuth2UserService.loadUser/서버 정보로 저장됐는지 확인/name:{}", user.getNickName());

        return new DefaultCustomOAuth2User(providerUser, userRequest);
    }

    private User findOrCreateUser(ProviderUser providerUser) {
        return userService.findByEmail(providerUser.getEmail())
                .orElseGet(() -> createUser(providerUser));
    }

    private User createUser(ProviderUser providerUser) {
        User user = User.builder()
                .email(providerUser.getEmail())
                .nickName(providerUser.getName())
                .password("OAUTH2_" + UUID.randomUUID())
                .userRole(UserRole.USER)
                .phoneNumber("000-0000-0000")
                .build();
        return userService.saveUser(user);
    }

    public ProviderUser getProviderUser(
            OAuth2User oAuth2User,
            ClientRegistration clientRegistration
    ) {
        String registrationId = clientRegistration.getRegistrationId();
        return switch (registrationId) {
            case "naver" -> new NaverUser(oAuth2User, clientRegistration);
            case "google" -> new GoogleUser(oAuth2User, clientRegistration);
            default -> throw new OAuthException(ErrorCode.INVALID_PROVIDER);
        };
    }
}