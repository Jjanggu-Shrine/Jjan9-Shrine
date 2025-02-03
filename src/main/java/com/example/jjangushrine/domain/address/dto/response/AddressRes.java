package com.example.jjangushrine.domain.address.dto.response;

import com.example.jjangushrine.domain.address.entity.Address;
import com.example.jjangushrine.domain.user.enums.UserRole;

public record AddressRes(
        Long addressId,
        UserRole userRole,
        Long ownerId,
        String recipientName,
        String addressName,
        String address,
        String addressDetail,
        String zipCode,
        Boolean isDefault
) {

    public static AddressRes from(Address address) {
        return new AddressRes(
                address.getId(),
                address.getUserRole(),
                address.getOwnerId(),
                address.getRecipientName(),
                address.getAddressName(),
                address.getAddress(),
                address.getAddressDetail(),
                address.getZipCode(),
                address.getIsDefault()
        );
    }
}
