package com.example.jjangushrine.domain.oauth2.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface ProviderUser {
    String getName();
    String getEmail();
    String getProvider();
    List<? extends GrantedAuthority> getAuthorities();
    Long getId();
    Map<String, Object> getAttributes();
}
