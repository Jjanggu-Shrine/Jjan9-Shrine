package com.example.jjangushrine.domain.address.dto.response;

import com.example.jjangushrine.domain.address.entity.Address;
import com.example.jjangushrine.domain.user.enums.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class AddressRes {
    private Long addressId;
    private UserRole userRole;
    private Long ownerId;
    private String recipientName;
    private String addressName;
    private String address;
    private String addressDetail;
    private String zipCode;
    private Boolean isDefault;

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
