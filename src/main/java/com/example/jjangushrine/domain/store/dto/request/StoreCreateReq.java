package com.example.jjangushrine.domain.store.dto.request;

import com.example.jjangushrine.domain.store.dto.StoreValidationMessage;
import com.example.jjangushrine.domain.store.entity.Store;
import com.example.jjangushrine.domain.user.entity.User;
import jakarta.validation.constraints.*;

public record StoreCreateReq(
        @NotBlank(message = StoreValidationMessage.BUSINESS_NUMBER_BLANK_MESSAGE)
        @Pattern(
                regexp = StoreValidationMessage.BUSINESS_NUMBER_REG,
                message = StoreValidationMessage.BUSINESS_NUMBER_INVALID_MESSAGE
        )
        String businessNumber,

        @NotBlank(message = StoreValidationMessage.BUSINESS_NAME_BLANK_MESSAGE)
        @Size(
                max = StoreValidationMessage.BUSINESS_NAME_MAX,
                message = StoreValidationMessage.BUSINESS_NAME_SIZE_MESSAGE
        )
        String businessName,

        @NotBlank(message = StoreValidationMessage.STORE_NAME_BLANK_MESSAGE)
        @Size(
                max = StoreValidationMessage.STORE_NAME_MAX,
                message = StoreValidationMessage.STORE_NAME_SIZE_MESSAGE)
        String storeName,

        @NotBlank(message = StoreValidationMessage.DESCRIPTION_BLANK_MESSAGE)
        @Size(
                max = StoreValidationMessage.DESCRIPTION_MAX,
                message = StoreValidationMessage.DESCRIPTION_SIZE_MESSAGE
        )
        String description,

        @NotNull(message = StoreValidationMessage.BASE_DELIVERY_FEE_BLANK_MESSAGE)
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

    public Store to(User user) {
        return Store.builder()
                .user(user)
                .businessNumber(this.businessNumber)
                .businessName(this.businessNumber)
                .storeName(this.storeName)
                .description(this.description)
                .baseDeliveryFee(this.baseDeliveryFee)
                .build();
    }
}
