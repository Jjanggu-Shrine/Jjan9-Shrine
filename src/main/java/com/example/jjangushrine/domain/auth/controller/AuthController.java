package com.example.jjangushrine.domain.auth.controller;

import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.request.UserSignUpReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.auth.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> userSignUp(
            @Valid @RequestBody UserSignUpReq userSignUpReq
    ) {
        userAuthService.userSignUp(userSignUpReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(ApiResMessage.SIGNUP_SUCCESS));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SignInRes>> userSignIn(
            @Valid @RequestBody SignInReq signInReq
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.LOGIN_SUCCESS,
                        userAuthService.userSignIn(signInReq))
                );
    }
}

