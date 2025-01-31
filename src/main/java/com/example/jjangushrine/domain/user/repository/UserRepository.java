package com.example.jjangushrine.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jjangushrine.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
