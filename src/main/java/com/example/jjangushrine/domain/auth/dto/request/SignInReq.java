package com.example.jjangushrine.domain.auth.dto.request;

import com.example.jjangushrine.domain.user.dto.UserValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignInReq(
        @NotBlank(message = UserValidationMessage.EMAIL_BLANK_MESSAGE)
        @Pattern(
                regexp = UserValidationMessage.EMAIL_REG,
                message = UserValidationMessage.INVALID_EMAIL_MESSAGE
        )
        String email,

        @NotBlank(message = UserValidationMessage.PASSWORD_BLANK_MESSAGE)
        String password
) {
}
