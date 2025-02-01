package com.example.jjangushrine.domain.auth.service;

import com.example.jjangushrine.domain.auth.dto.request.SellerSignUpReq;
import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.request.UserSignUpReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.service.SellerService;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.common.ConflictException;
import com.example.jjangushrine.exception.common.InvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
class SellerAuthServiceTest {
    @Autowired
    private SellerAuthService authService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("판매자 회원가입 성공")
    void sellerSignUpSuccess() {
        // given
        SellerSignUpReq req = new SellerSignUpReq(
                "seller@test.com",
                "Password123!",
                "대표자명",
                "010-1234-5678",
                "서울시",
                "강남구",
                "12345"
        );

        // when
        authService.sellerSignUp(req);

        // then
        Seller seller = sellerService.findByEmail(req.email())
                .orElseThrow();

        assertThat(seller.getEmail()).isEqualTo(req.email());
        assertThat(passwordEncoder.matches(req.password(), seller.getPassword())).isTrue();
        assertThat(seller.getUserRole()).isEqualTo(UserRole.SELLER);
    }

    @Test
    @DisplayName("판매자 로그인 성공")
    void sellerSignInSuccess() {
        // given
        String email = "seller@test.com";
        String password = "Password123!";

        SellerSignUpReq signUpReq = new SellerSignUpReq(
                email, password, "대표자명", "010-1234-5678",
                "서울시", "강남구", "12345"
        );
        authService.sellerSignUp(signUpReq);

        SignInReq signInReq = new SignInReq(email, password);

        // when
        SignInRes res = authService.sellerSignIn(signInReq);

        // then
        assertThat(res.bearerToken()).startsWith("Bearer ");
    }

    @Test
    @DisplayName("판매자 잘못된 비밀번호로 로그인 실패")
    void sellerSignInFailWithWrongPassword() {
        // given
        String email = "seller@test.com";
        String password = "Password123!";

        SellerSignUpReq signUpReq = new SellerSignUpReq(
                email, password, "대표자명", "010-1234-5678",
                "서울시", "강남구", "12345"
        );
        authService.sellerSignUp(signUpReq);

        SignInReq signInReq = new SignInReq(email, "wrongpassword!");

        // when & then
        assertThatThrownBy(() -> authService.sellerSignIn(signInReq))
                .isInstanceOf(InvalidException.class)
                .hasMessage("잘못된 아이디 또는 비밀번호입니다.");
    }

    @Test
    @DisplayName("판매자 이메일 중복 가입 실패")
    void sellerSignUpFailWithDuplicateEmail() {
        // given
        String email = "seller@test.com";
        SellerSignUpReq req = new SellerSignUpReq(
                email, "Password123!", "대표자명", "010-1234-5678",
                "서울시", "강남구", "12345"
        );
        authService.sellerSignUp(req);

        // when & then
        assertThatThrownBy(() -> authService.sellerSignUp(req))
                .isInstanceOf(ConflictException.class);
    }
}