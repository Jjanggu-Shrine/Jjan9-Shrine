package com.example.jjangushrine.domain.oauth2.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class GoogleUser extends OAuth2ProviderUser {

    public GoogleUser(
            OAuth2User oAuth2User,
            ClientRegistration clientRegistration,
            Long id
    ) {
        super(
                oAuth2User.getAttributes(),
                oAuth2User,
                clientRegistration,
                id
        );
    }

    @Override
    public String getName() {
        return (String) getAttributes().get("name");
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }
}
