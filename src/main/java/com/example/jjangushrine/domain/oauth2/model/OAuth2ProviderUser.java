package com.example.jjangushrine.domain.oauth2.model;

import com.example.jjangushrine.domain.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class OAuth2ProviderUser implements ProviderUser {

    protected final OAuth2User oAuth2User;
    protected final ClientRegistration clientRegistration;

    @Getter
    private final Long id;

    @Getter
    private final Map<String, Object> attributes;

    public OAuth2ProviderUser(
            Map<String, Object> attributes,
            OAuth2User oAuth2User,
            ClientRegistration clientRegistration,
            Long id
    ) {
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
        this.id = id;
    }

    @Override
    public abstract String getName();

    @Override
    public abstract String getEmail();

    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(UserRole.USER.name())
        );
    }
}