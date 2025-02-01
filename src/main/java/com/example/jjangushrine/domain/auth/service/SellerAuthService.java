package com.example.jjangushrine.domain.auth.service;

import com.example.jjangushrine.config.security.jwt.JwtUtil;
import com.example.jjangushrine.config.security.sevice.CustomUserDetailsService;
import com.example.jjangushrine.domain.auth.dto.request.SellerSignUpReq;
import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.service.SellerService;
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
public class SellerAuthService {

    private final SellerService sellerService;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void sellerSignUp(SellerSignUpReq sellerSignUpReq) {
        if (sellerService.existsByEmail(sellerSignUpReq.email())) {
            throw new ConflictException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(sellerSignUpReq.password());
        Seller newSeller = sellerSignUpReq.to(encodedPassword);

        sellerService.saveSeller(newSeller);
    }

    public SignInRes sellerSignIn(SignInReq signInReq) {
        Seller seller = sellerService.findByEmail(signInReq.email())
                .orElseThrow(() -> new InvalidException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(signInReq.password(), seller.getPassword())) {
            throw new InvalidException(ErrorCode.LOGIN_FAILED);
        }

        Authentication authentication = customUserDetailsService.createAuthentication(seller);
        String token = jwtUtil.generateToken(authentication);

        return new SignInRes(token);
    }
}
