package com.example.jjangushrine.domain.seller.repository;

import com.example.jjangushrine.domain.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    boolean existsByEmail(String email);

    Optional<Seller> findByEmail(String email);

    Optional<Seller> findByEmailAndIsDeletedIsFalse(String email);

    Optional<Seller> findSellerByIdAndIsDeletedIsFalse(Long id);
}
