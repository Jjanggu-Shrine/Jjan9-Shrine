package com.example.jjangushrine.domain.seller.dto.response;

import com.example.jjangushrine.domain.seller.entity.Seller;

public record SellerRes(
        Long sellerId,
        String email,
        String representativeName,
        String phoneNumber
) {
    public static SellerRes from(Seller seller) {
        return new SellerRes(
                seller.getId(),
                seller.getEmail(),
                seller.getRepresentativeName(),
                seller.getPhoneNumber()
        );
    }
}

