package com.example.jjangushrine.domain.store.repository;

import com.example.jjangushrine.domain.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jjangushrine.domain.store.entity.Store;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findBySellerAndIsDeletedIsFalse(Seller seller);

    int countBySellerAndIsDeletedIsFalse(Seller seller);

    boolean existsByBusinessNumberAndIsDeletedIsFalse(String BusinessNumber);

    Optional<Store> findByIdAndIsDeletedIsFalse(long id);
}
