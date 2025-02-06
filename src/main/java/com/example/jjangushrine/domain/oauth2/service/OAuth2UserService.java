package com.example.jjangushrine.domain.oauth2.service;

import com.example.jjangushrine.domain.oauth2.model.GoogleUser;
import com.example.jjangushrine.domain.oauth2.model.NaverUser;
import com.example.jjangushrine.domain.oauth2.model.ProviderUser;
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

        User user = findOrCreateUser
                (oAuth2User, userRequest.getClientRegistration());

        ProviderUser providerUser = getProviderUser
                (oAuth2User, userRequest.getClientRegistration(), user.getId());

        return new DefaultCustomOAuth2User(providerUser, userRequest);
    }

    private User findOrCreateUser(
            OAuth2User oAuth2User,
            ClientRegistration clientRegistration
    ) {
        ProviderUser providerUser =
                getProviderUser(oAuth2User, clientRegistration, null);
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
            ClientRegistration clientRegistration,
            Long id
    ) {
        String registrationId = clientRegistration.getRegistrationId();
        return switch (registrationId) {
            case "naver" -> new NaverUser(oAuth2User, clientRegistration, id);
            case "google" -> new GoogleUser(oAuth2User, clientRegistration, id);
            default -> throw new OAuthException(ErrorCode.INVALID_PROVIDER);
        };
    }
}