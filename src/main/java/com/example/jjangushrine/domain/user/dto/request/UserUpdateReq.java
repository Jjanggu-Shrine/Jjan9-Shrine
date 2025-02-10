package com.example.jjangushrine.domain.user.dto.request;

import com.example.jjangushrine.domain.user.dto.UserValidationMessage;
import com.example.jjangushrine.domain.user.enums.UserRole;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateReq(
    @Size(min = UserValidationMessage.PASSWORD_MIN,
            message = UserValidationMessage.PASSWORD_MIN_MESSAGE)
    @Pattern(
            regexp = UserValidationMessage.PASSWORD_REG,
            message = UserValidationMessage.INVALID_PASSWORD_MESSAGE
    )
    String password,

    @Size(
            min = UserValidationMessage.NICKNAME_MIN,
            max = UserValidationMessage.NICKNAME_MAX,
            message = UserValidationMessage.NICKNAME_RANGE_MESSAGE
    )
    String nickName,

    @Pattern(regexp = UserValidationMessage.PHONE_NUMBER_REG,
            message = UserValidationMessage.INVALID_PHONE_NUMBER_MESSAGE)
    String phoneNumber
) {
}
