package com.example.jjangushrine.domain.seller.service;

import com.example.jjangushrine.domain.seller.dto.request.SellerUpdateReq;
import com.example.jjangushrine.domain.seller.dto.response.SellerRes;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.repository.SellerRepository;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService {

    private final SellerRepository sellerRepository;

    public SellerRes getSellerInfo(String email) {
        Seller seller = findSellerByEmail(email);
        return SellerRes.from(seller);
    }

    @Transactional
    public SellerRes updateSeller(SellerUpdateReq updateReq, String email) {
        Seller seller = findSellerByEmail(email);
        seller.update(updateReq);
        return SellerRes.from(seller);
    }

    @Transactional
    public void deleteSeller(String email) {
        Seller seller = findSellerByEmail(email);
        seller.softDelete();
    }

    public boolean existsByEmail(String email) {
        return sellerRepository.existsByEmail(email);
    }

    public void saveSeller(Seller seller) {
        sellerRepository.save(seller);
    }

    public Optional<Seller> findByEmail(String email) {
        return sellerRepository.findByEmail(email);
    }

    public Seller findSellerByEmail(String email) {
        return sellerRepository.findByEmailAndIsDeletedIsFalse(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SELLER_NOT_FOUND));
    }

    public Seller findSellerById(Long id) {
        return sellerRepository.findSellerByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SELLER_NOT_FOUND));
    }
}
