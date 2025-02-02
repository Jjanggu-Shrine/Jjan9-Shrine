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
        String phoneNumber,

        @NotBlank(message = UserValidationMessage.ADDRESS_BLANK_MESSAGE)
        @Size(max = UserValidationMessage.ADDRESS_MAX,
                message = UserValidationMessage.ADDRESS_LENGTH_MESSAGE)
        String address,

        @NotBlank(message = UserValidationMessage.ADDRESS_DETAIL_BLANK_MESSAGE)
        @Size(max = UserValidationMessage.ADDRESS_DETAIL_MAX,
                message = UserValidationMessage.ADDRESS_DETAIL_LENGTH_MESSAGE)
        String addressDetail,

        @NotBlank(message = UserValidationMessage.ZIP_CODE_BLANK_MESSAGE)
        @Pattern(regexp = UserValidationMessage.ZIP_CODE_REG,
                message = UserValidationMessage.INVALID_ZIP_CODE_MESSAGE)
        String zipCode
) {

        public Seller to(String encodedPassword) {
                return Seller.builder()
                        .email(this.email())
                        .password(encodedPassword)
                        .representativeName(this.representativeName())
                        .phoneNumber(this.phoneNumber())
                        .address(this.address())
                        .addressDetail(this.addressDetail())
                        .zipCode(this.zipCode())
                        .build();
        }
}
