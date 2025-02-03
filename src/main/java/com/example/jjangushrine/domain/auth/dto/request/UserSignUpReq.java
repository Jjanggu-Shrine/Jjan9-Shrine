package com.example.jjangushrine.domain.auth.dto.request;

import com.example.jjangushrine.domain.user.dto.UserValidationMessage;
import com.example.jjangushrine.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignUpReq(
        @NotBlank(message = UserValidationMessage.EMAIL_BLANK_MESSAGE)
        @Pattern(
                regexp = UserValidationMessage.EMAIL_REG,
                message = UserValidationMessage.INVALID_EMAIL_MESSAGE
        )
        String email,

        @NotBlank(message = UserValidationMessage.PASSWORD_BLANK_MESSAGE)
        @Size(min = UserValidationMessage.PASSWORD_MIN, message = UserValidationMessage.PASSWORD_MIN_MESSAGE)
        @Pattern(
                regexp = UserValidationMessage.PASSWORD_REG,
                message = UserValidationMessage.INVALID_PASSWORD_MESSAGE
        )
        String password,

        @NotBlank(message = UserValidationMessage.NICKNAME_BLANK_MESSAGE)
        @Size(
                min = UserValidationMessage.NICKNAME_MIN,
                max = UserValidationMessage.NICKNAME_MAX,
                message = UserValidationMessage.NICKNAME_RANGE_MESSAGE
        )
        String nickName,

        @NotBlank(message = UserValidationMessage.PHONE_NUMBER_BLANK_MESSAGE)
        @Pattern(regexp = UserValidationMessage.PHONE_NUMBER_REG,
                message = UserValidationMessage.INVALID_PHONE_NUMBER_MESSAGE)
        String phoneNumber
) {

        public User to(String encodedPassword) {
                return User.builder()
                        .email(this.email())
                        .password(encodedPassword)
                        .nickName(this.nickName())
                        .phoneNumber(this.phoneNumber())
                        .build();
        }
}
