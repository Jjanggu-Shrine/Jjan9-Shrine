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
        Seller seller = findUserByEmail(email);
        return SellerRes.from(seller);
    }

    @Transactional
    public SellerRes updateUser(SellerUpdateReq updateReq, String email) {
        Seller seller = findUserByEmail(email);
        seller.update(updateReq);
        return SellerRes.from(seller);
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

    public Seller findUserByEmail(String email) {
        return sellerRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
