package com.example.jjangushrine.domain.address.repository;

import com.example.jjangushrine.domain.address.entity.Address;
import com.example.jjangushrine.domain.user.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    int countByOwnerIdAndUserRole(Long id, UserRole userRole);

    Page<Address> findAllByOwnerIdAndUserRole(Long id, UserRole userRole, Pageable pageable);

    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.ownerId = :ownerId AND a.userRole = :userRole")
    void setAllAddressesToNonDefault(@Param("ownerId") Long ownerId, @Param("userRole") UserRole userRole);

    Optional<Address> findByIdAndIsDeletedFalse(Long addressId);
}
