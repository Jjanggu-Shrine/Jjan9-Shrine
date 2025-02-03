package com.example.jjangushrine.domain.address.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
public class AddressPageRequest {
    private Integer page = 0;

    public PageRequest toPageRequest() {
        return PageRequest.of(
                page,
                5,
                Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}

