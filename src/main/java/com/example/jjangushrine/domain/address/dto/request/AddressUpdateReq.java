package com.example.jjangushrine.domain.address.dto.request;

import com.example.jjangushrine.domain.address.dto.AddressValidationMessage;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressUpdateReq {
    @Size(
            min = AddressValidationMessage.RECIPIENT_NAME_MIN,
            max = AddressValidationMessage.RECIPIENT_NAME_MAX,
            message = AddressValidationMessage.RECIPIENT_NAME_LENGTH_MESSAGE
    )
    private String recipientName;

    @Size(
            max = AddressValidationMessage.ADDRESS_NAME_MAX,
            message = AddressValidationMessage.ADDRESS_NAME_LENGTH_MESSAGE
    )
    private String addressName;

    @Size(
            max = AddressValidationMessage.ADDRESS_MAX,
            message = AddressValidationMessage.ADDRESS_LENGTH_MESSAGE
    )
    private String address;

    @Size(
            max = AddressValidationMessage.ADDRESS_DETAIL_MAX,
            message = AddressValidationMessage.ADDRESS_DETAIL_LENGTH_MESSAGE
    )
    private String addressDetail;

    @Pattern(
            regexp = AddressValidationMessage.ZIP_CODE_REG,
            message = AddressValidationMessage.INVALID_ZIP_CODE_MESSAGE
    )
    private String zipCode;
}
