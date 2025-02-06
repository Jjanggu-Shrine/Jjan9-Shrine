package com.example.jjangushrine.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jjangushrine.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsDeletedIsFalse(String email);

    Optional<User> findByIdAndIsDeletedIsFalse(Long id);
}
