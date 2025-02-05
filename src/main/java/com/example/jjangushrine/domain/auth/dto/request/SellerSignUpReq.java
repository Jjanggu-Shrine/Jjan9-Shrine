package com.example.jjangushrine.domain.auth.dto.request;

import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.user.dto.UserValidationMessage;
import com.example.jjangushrine.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SellerSignUpReq(
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

        @NotBlank(message = UserValidationMessage.REPR_NAME_BLANK_MESSAGE)
        @Size(
                min = UserValidationMessage.REPR_NAME_MIN,
                max = UserValidationMessage.REPR_NAME_MAX,
                message = UserValidationMessage.REPR_NAME_SIZE_MESSAGE
        )
        @Pattern(
                regexp = UserValidationMessage.REPR_NAME_REG,
                message = UserValidationMessage.INVALID_REPR_NAME_MESSAGE
        )
        String representativeName,

        @NotBlank(message = UserValidationMessage.PHONE_NUMBER_BLANK_MESSAGE)
        @Pattern(regexp = UserValidationMessage.PHONE_NUMBER_REG,
                message = UserValidationMessage.INVALID_PHONE_NUMBER_MESSAGE)
        String phoneNumber
) {

        public Seller to(String encodedPassword) {
                return Seller.builder()
                        .email(this.email())
                        .password(encodedPassword)
                        .representativeName(this.representativeName())
                        .phoneNumber(this.phoneNumber())
                        .build();
        }
}
