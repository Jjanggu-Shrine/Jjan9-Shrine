package com.example.jjangushrine.domain.address.repository;

import com.example.jjangushrine.domain.address.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    int countByIdAndIsDeletedIsFalse(Long id);

    Page<Address> findAllByUserIdAndIsDeletedIsFalse(Long id, Pageable pageable);

    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.id = :id")
    void setAllAddressesToNonDefault(@Param("id") Long id);

    Optional<Address> findByIdAndIsDeletedIsFalse(Long addressId);

    Optional<Address> findByIdAndIsDefaultIsTrue(Long id);

    int countByUserIdAndIsDeletedIsFalse(Long id);
}
