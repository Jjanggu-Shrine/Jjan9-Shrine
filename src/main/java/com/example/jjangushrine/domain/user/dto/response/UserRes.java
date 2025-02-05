package com.example.jjangushrine.domain.user.dto.response;

import com.example.jjangushrine.domain.user.entity.User;

public record UserRes(
        Long userId,
        String email,
        String nickName,
        String phoneNumber
) {
    public static UserRes from(User user) {
        return new UserRes(
                user.getId(),
                user.getEmail(),
                user.getNickName(),
                user.getPhoneNumber()
        );
    }
}
