package com.example.jjangushrine.config.security.sevice;

import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return CustomUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getUserRole())
                .build();
    }

    public static Authentication createAuthentication(Long id, String email, UserRole role) {
        CustomUserDetails userDetails;

       userDetails = CustomUserDetails.builder()
               .id(id)
               .email(email)
               .role(role)
               .build();

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
