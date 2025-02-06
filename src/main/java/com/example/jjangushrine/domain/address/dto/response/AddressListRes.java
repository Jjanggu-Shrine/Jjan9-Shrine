package com.example.jjangushrine.domain.address.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AddressListRes(
        List<AddressRes> addresses
) {
}
