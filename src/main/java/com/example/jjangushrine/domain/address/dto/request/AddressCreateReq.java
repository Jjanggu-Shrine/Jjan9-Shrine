package com.example.jjangushrine.domain.address.dto.request;

import com.example.jjangushrine.domain.address.dto.AddressValidationMessage;
import com.example.jjangushrine.domain.address.entity.Address;
import com.example.jjangushrine.domain.user.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressCreateReq(
        @NotBlank(message = AddressValidationMessage.RECIPIENT_NAME_BLANK_MESSAGE)
        @Size(
                min = AddressValidationMessage.RECIPIENT_NAME_MIN,
                max = AddressValidationMessage.RECIPIENT_NAME_MAX,
                message = AddressValidationMessage.RECIPIENT_NAME_LENGTH_MESSAGE
        )
        String recipientName,

        @NotBlank(message = AddressValidationMessage.ADDRESS_NAME_BLANK_MESSAGE)
        @Size(
                max = AddressValidationMessage.ADDRESS_NAME_MAX,
                message = AddressValidationMessage.ADDRESS_NAME_LENGTH_MESSAGE
        )
        String addressName,

        @NotBlank(message = AddressValidationMessage.ADDRESS_BLANK_MESSAGE)
        @Size(
                max = AddressValidationMessage.ADDRESS_MAX,
                message = AddressValidationMessage.ADDRESS_LENGTH_MESSAGE
        )
        String address,

        @NotBlank(message = AddressValidationMessage.ADDRESS_DETAIL_BLANK_MESSAGE)
        @Size(
                max = AddressValidationMessage.ADDRESS_DETAIL_MAX,
                message = AddressValidationMessage.ADDRESS_DETAIL_LENGTH_MESSAGE
        )
        String addressDetail,

        @NotBlank(message = AddressValidationMessage.ZIP_CODE_BLANK_MESSAGE)
        @Pattern(
                regexp = AddressValidationMessage.ZIP_CODE_REG,
                message = AddressValidationMessage.INVALID_ZIP_CODE_MESSAGE
        )
        String zipCode
) {

    public Address to(UserRole userRole, Long id) {
        return Address.builder()
                .ownerId(id)
                .userRole(userRole)
                .recipientName(this.recipientName)
                .addressName(this.addressName)
                .address(this.address)
                .addressDetail(this.addressDetail)
                .zipCode(this.zipCode)
                .build();
    }
}
