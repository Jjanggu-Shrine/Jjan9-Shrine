package com.example.jjangushrine.domain.auth.controller;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.config.TestSecurityConfig;
import com.example.jjangushrine.domain.auth.dto.request.SignInReq;
import com.example.jjangushrine.domain.auth.dto.request.UserSignUpReq;
import com.example.jjangushrine.domain.auth.dto.response.SignInRes;
import com.example.jjangushrine.domain.auth.service.SellerAuthService;
import com.example.jjangushrine.domain.auth.service.UserAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureWebMvc
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthService userAuthService;

    @MockBean
    private SellerAuthService sellerAuthService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 API 성공")
    void signUpApiSuccess() throws Exception {
        // given
        UserSignUpReq req = new UserSignUpReq(
                "usermail@mail.com",
                "1234", "userNick",
                "000-111-2222");

        // when & then
        mockMvc.perform(post("/api/v1/auth/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(userAuthService).userSignUp(req);
    }

    @Test
    @DisplayName("로그인 API 성공")
    void signInApiSuccess() throws Exception {
        // given
        SignInReq req = new SignInReq("usermail@mail.com", "!2345");
        SignInRes res = new SignInRes("Bearer token");
        when(userAuthService.userSignIn(req)).thenReturn(res);

        // when & then
        mockMvc.perform(post("/api/v1/auth/user/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value(ApiResMessage.LOGIN_SUCCESS))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andDo(print());
    }
}