package com.example.jjangushrine.domain.seller.service;

import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public boolean existsByEmail(String email) {
        return sellerRepository.existsByEmail(email);
    }

    public Optional<Seller> findByEmail(String email) {
        return sellerRepository.findByEmail(email);
    }

    public void saveSeller(Seller seller) {
        sellerRepository.save(seller);
    }
}
