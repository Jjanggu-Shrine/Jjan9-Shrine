package com.example.jjangushrine.domain.store.repository;

import com.example.jjangushrine.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jjangushrine.domain.store.entity.Store;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByBusinessNumberAndIsDeletedIsFalse(String BusinessNumber);

    Optional<Store> findByIdAndIsDeletedIsFalse(long id);

    int countByUserAndIsDeletedIsFalse(User user);

    Optional<Store> findByUserAndIsDeletedIsFalse(User user);
}
