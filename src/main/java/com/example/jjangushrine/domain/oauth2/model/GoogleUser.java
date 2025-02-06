package com.example.jjangushrine.domain.oauth2.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class GoogleUser extends OAuth2ProviderUser{

    public GoogleUser(
            OAuth2User oAuth2User,
            ClientRegistration clientRegistration
    ) {
        super(
                oAuth2User.getAttributes(),
                oAuth2User,
                clientRegistration
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

    @Override
    public void setId(Long id) {
        super.setId(id);
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("userId", id);
    }
}
