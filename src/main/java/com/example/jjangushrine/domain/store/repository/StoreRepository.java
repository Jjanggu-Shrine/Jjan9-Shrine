package com.example.jjangushrine.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jjangushrine.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
