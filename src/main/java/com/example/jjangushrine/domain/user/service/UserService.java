package com.example.jjangushrine.domain.user.service;

import com.example.jjangushrine.domain.user.dto.request.UserUpdateReq;
import com.example.jjangushrine.domain.user.dto.response.UserRes;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.repository.UserRepository;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserRes getUserInfo(String email) {
        User user = findUserByEmail(email);
        return UserRes.from(user);
    }

    @Transactional
    public UserRes updateUser(UserUpdateReq userUpdateReq, String email) {
        User user = findUserByEmail(email);
        user.update(userUpdateReq);
        return UserRes.from(user);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = findUserByEmail(email);
        user.softDelete();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
