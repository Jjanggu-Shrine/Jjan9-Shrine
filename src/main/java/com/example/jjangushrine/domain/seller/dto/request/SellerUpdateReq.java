package com.example.jjangushrine.domain.seller.dto.request;

import com.example.jjangushrine.domain.user.dto.UserValidationMessage;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SellerUpdateReq(
        @Size(min = UserValidationMessage.PASSWORD_MIN, message = UserValidationMessage.PASSWORD_MIN_MESSAGE)
        @Pattern(
                regexp = UserValidationMessage.PASSWORD_REG,
                message = UserValidationMessage.INVALID_PASSWORD_MESSAGE
        )
        String password,

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

        @Pattern(regexp = UserValidationMessage.PHONE_NUMBER_REG,
                message = UserValidationMessage.INVALID_PHONE_NUMBER_MESSAGE)
        String phoneNumber
) {
}
