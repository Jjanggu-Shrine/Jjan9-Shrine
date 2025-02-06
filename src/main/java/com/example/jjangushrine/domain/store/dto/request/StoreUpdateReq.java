package com.example.jjangushrine.domain.store.dto.request;

import com.example.jjangushrine.domain.store.dto.StoreValidationMessage;
import jakarta.validation.constraints.*;

public record StoreUpdateReq(
        @Pattern(
                regexp = StoreValidationMessage.BUSINESS_NUMBER_REG,
                message = StoreValidationMessage.BUSINESS_NUMBER_INVALID_MESSAGE
        )
        String businessNumber,

        @Size(
                max = StoreValidationMessage.BUSINESS_NAME_MAX,
                message = StoreValidationMessage.BUSINESS_NAME_SIZE_MESSAGE
        )
        String businessName,

        @Size(
                max = StoreValidationMessage.STORE_NAME_MAX,
                message = StoreValidationMessage.STORE_NAME_SIZE_MESSAGE)
        String storeName,

        @Size(
                max = StoreValidationMessage.DESCRIPTION_MAX,
                message = StoreValidationMessage.DESCRIPTION_SIZE_MESSAGE
        )
        String description,

        @Min(
                value = StoreValidationMessage.BASE_DELIVERY_FEE_MIN,
                message = StoreValidationMessage.BASE_DELIVERY_FEE_MIN_MESSAGE
        )
        @Max(
                value = StoreValidationMessage.BASE_DELIVERY_FEE_MAX,
                message = StoreValidationMessage.BASE_DELIVERY_FEE_MAX_MESSAGE
        )
        Short baseDeliveryFee
) {
}
