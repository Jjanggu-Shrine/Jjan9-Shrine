package com.example.jjangushrine.domain.address.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public record AddressPageRequest(
        Integer page
) {
    public AddressPageRequest() {
        this(0);
    }

    public PageRequest toPageRequest() {
        return PageRequest.of(
                page,
                5,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }
}

