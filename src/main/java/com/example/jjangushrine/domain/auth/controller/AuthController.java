package com.example.jjangushrine.domain.auth.controller;

import com.example.jjangushrine.domain.auth.dto.request.SellerSignUpReq;
import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.request.UserSignUpReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.auth.service.SellerAuthService;
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
    private final SellerAuthService sellerAuthService;

    @PostMapping("user/signup")
    public ResponseEntity<Void> userSignUp(
            @RequestBody @Valid UserSignUpReq userSignUpReq
    ) {
        userAuthService.userSignUp(userSignUpReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("seller/signup")
    public ResponseEntity<Void> sellerSignUp(
            @RequestBody @Valid SellerSignUpReq sellerSignUpReq
    ) {
        sellerAuthService.sellerSignUp(sellerSignUpReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("user/signin")
    public ResponseEntity<SignInRes> userSignIn(
            @RequestBody @Valid SignInReq signInReq
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userAuthService.userSignIn(signInReq));
    }

    @PostMapping("seller/signin")
    public ResponseEntity<SignInRes> sellerSignIn(
            @RequestBody @Valid SignInReq signInReq
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sellerAuthService.sellerSignIn(signInReq));
    }
}

