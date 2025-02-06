package com.example.jjangushrine.domain.oauth2.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class NaverUser extends OAuth2ProviderUser {
    private final Map<String, Object> response;

    @SuppressWarnings("unchecked")
    public NaverUser(
            OAuth2User oAuth2User,
            ClientRegistration clientRegistration
    ) {
        super(
                (Map<String, Object>) oAuth2User.getAttributes().get("response"),
                oAuth2User,
                clientRegistration
        );
        this.response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
    }

    @Override
    public String getName() {
        return response.get("name").toString();
    }

    @Override
    public String getEmail() {
        return response.get("email").toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return response;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        response.put("userId", id);
    }
}
