package com.example.jjangushrine.domain.store.dto.response;

import com.example.jjangushrine.domain.store.entity.Store;

public record StoreRes(
        Long storeId,
        Long selleId,
        String businessNumber,
        String businessName,
        String storeName,
        String description,
        Short baseDeliveryFee
) {
    public static StoreRes from(Store store) {
        return new StoreRes(
                store.getId(),
                store.getSeller().getId(),
                store.getBusinessNumber(),
                store.getBusinessName(),
                store.getStoreName(),
                store.getDescription(),
                store.getBaseDeliveryFee()
        );
    }
}
