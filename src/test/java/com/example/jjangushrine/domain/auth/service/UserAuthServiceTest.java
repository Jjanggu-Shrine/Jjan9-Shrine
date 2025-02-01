package com.example.jjangushrine.domain.auth.service;

import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.request.UserSignUpReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.seller.service.SellerService;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.common.ConflictException;
import com.example.jjangushrine.exception.common.InvalidException;
import com.example.jjangushrine.exception.common.NotFoundException;
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
class UserAuthServiceTest {
    @Autowired
    private UserAuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("일반 회원가입 성공")
    void userSignUpSuccess() {
        // given
        UserSignUpReq req = new UserSignUpReq(
                "test@test.com",
                "Password123!",
                "닉네임",
                "010-1234-5678",
                "서울시",
                "강남구",
                "12345"
        );

        // when
        authService.userSignUp(req);

        // then
        User user = userService.findByEmail(req.email())
                .orElseThrow();

        assertThat(user.getEmail()).isEqualTo(req.email());
        assertThat(passwordEncoder.matches(req.password(), user.getPassword())).isTrue();
        assertThat(user.getUserRole()).isEqualTo(UserRole.USER);
    }

    @Test
    @DisplayName("이메일 중복 시 회원가입 실패")
    void signUpFailWithDuplicateEmail() {
        // given
        String email = "test@test.com";
        UserSignUpReq req = new UserSignUpReq(
                "usermail@mail.com",
                "1234", "userNick",
                "000-111-2222",
                "address",
                "addressDetail",
                "1234567");
        authService.userSignUp(req);  // 첫 번째 가입

        UserSignUpReq duplicateReq = new UserSignUpReq(
                "usermail@mail.com",
                "1234", "userNick",
                "000-111-2222",
                "address",
                "addressDetail",
                "1234567");

        // when & then
        assertThatThrownBy(() -> authService.userSignUp(duplicateReq))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    @DisplayName("로그인 성공")
    void signInSuccess() {
        // given
        String email = "test@test.com";
        String password = "password123!";

        // 먼저 회원가입
        UserSignUpReq signUpReq = new UserSignUpReq(
                email,
                password,
                "nickname",
                "010-1234-5678",
                "address",
                "addressDetail",
                "12345"
        );
        authService.userSignUp(signUpReq);

        // 로그인 시도
        SignInReq signInReq = new SignInReq(email, password);

        // when
        SignInRes res = authService.userSignIn(signInReq);

        // then
        assertThat(res.bearerToken()).startsWith("Bearer ");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 실패")
    void signInFailWithWrongPassword() {
        // given
        String email = "test@test.com";
        String password = "password123!";

        // 회원가입
        UserSignUpReq signUpReq = new UserSignUpReq(
                email,
                password,
                "nickname",
                "010-1234-5678",
                "address",
                "addressDetail",
                "12345"
        );
        authService.userSignUp(signUpReq);

        // 잘못된 비밀번호로 로그인 시도
        SignInReq signInReq = new SignInReq(email, "wrongpassword!");

        // when & then
        assertThatThrownBy(() -> authService.userSignIn(signInReq))
                .isInstanceOf(InvalidException.class)
                .hasMessage("잘못된 아이디 또는 비밀번호입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 실패")
    void signInFailWithNonExistentEmail() {
        // given
        SignInReq signInReq = new SignInReq("nonexistent@test.com", "password123!");

        // when & then
        assertThatThrownBy(() -> authService.userSignIn(signInReq))
                .isInstanceOf(InvalidException.class)
                .hasMessage("잘못된 아이디 또는 비밀번호입니다.");
    }
}