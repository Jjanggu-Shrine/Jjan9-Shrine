package com.example.jjangushrine.domain.auth.service;

import com.example.jjangushrine.config.security.jwt.JwtUtil;
import com.example.jjangushrine.config.security.sevice.CustomUserDetailsService;
import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.request.UserSignUpReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ConflictException;
import com.example.jjangushrine.exception.common.InvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthService {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void userSignUp(UserSignUpReq authSignupReq) {
        if (userService.existsByEmail(authSignupReq.email())) {
            throw new ConflictException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(authSignupReq.password());
        User newUser = authSignupReq.to(encodedPassword);

        userService.saveUser(newUser);
    }

    public SignInRes userSignIn(SignInReq signInReq) {
        User user = userService.findByEmail(signInReq.email())
                .orElseThrow(() -> new InvalidException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(signInReq.password(), user.getPassword())) {
            throw new InvalidException(ErrorCode.LOGIN_FAILED);
        }

        Authentication authentication = customUserDetailsService.createAuthentication(user);
        String token = jwtUtil.generateToken(authentication);

        return new SignInRes(token);
    }
}
