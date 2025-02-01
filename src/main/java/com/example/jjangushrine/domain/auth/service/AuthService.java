package com.example.jjangushrine.domain.auth.service;

import com.example.jjangushrine.config.security.jwt.JwtUtil;
import com.example.jjangushrine.config.security.sevice.CustomUserDetailsService;
import com.example.jjangushrine.domain.auth.dto.request.SellerSignUpReq;
import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.request.UserSignUpReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.service.SellerService;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ConflictException;
import com.example.jjangushrine.exception.common.InvalidException;
import com.example.jjangushrine.exception.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserService userService;
    private final SellerService sellerService;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void userSignUp(UserSignUpReq authSignupReq) {
        validateDuplicateEmail(authSignupReq.email());

        User newUser = User.builder()
                .email(authSignupReq.email())
                .password(encodePassword(authSignupReq.password()))
                .nickName(authSignupReq.nickName())
                .phoneNumber(authSignupReq.phoneNumber())
                .address(authSignupReq.address())
                .addressDetail(authSignupReq.addressDetail())
                .zipCode(authSignupReq.zipCode())
                .build();

        userService.saveUser(newUser);
    }

    @Transactional
    public void sellerSignUp(SellerSignUpReq sellerSignUpReq) {
        validateDuplicateEmail(sellerSignUpReq.email());

        Seller newSeller = Seller.builder()
                .email(sellerSignUpReq.email())
                .password(encodePassword(sellerSignUpReq.password()))
                .representativeName(sellerSignUpReq.representativeName())
                .phoneNumber(sellerSignUpReq.phoneNumber())
                .address(sellerSignUpReq.address())
                .addressDetail(sellerSignUpReq.addressDetail())
                .zipCode(sellerSignUpReq.zipCode())
                .build();

        sellerService.saveSeller(newSeller);
    }

    public SignInRes userSignIn(SignInReq signInReq) {
        User user = userService.findByEmail(signInReq.email())
                .orElseThrow(() -> new NotFoundException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(signInReq.password(), user.getPassword())) {
            throw new InvalidException(ErrorCode.LOGIN_FAILED);
        }

        Authentication authentication = customUserDetailsService.createAuthentication(user);
        String token = jwtUtil.generateToken(authentication);

        return new SignInRes(token);
    }

    public SignInRes sellerSignIn(SignInReq signInReq) {
        Seller seller = sellerService.findByEmail(signInReq.email())
                .orElseThrow(() -> new NotFoundException(ErrorCode.LOGIN_FAILED));

        if (!passwordEncoder.matches(signInReq.password(), seller.getPassword())) {
            throw new InvalidException(ErrorCode.LOGIN_FAILED);
        }

        Authentication authentication = customUserDetailsService.createAuthentication(seller);
        String token = jwtUtil.generateToken(authentication);

        return new SignInRes(token);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateDuplicateEmail(String email) {
        if (userService.existsByEmail(email) || sellerService.existsByEmail(email)) {
            throw new ConflictException(ErrorCode.DUPLICATE_EMAIL);
        }
    }
}
