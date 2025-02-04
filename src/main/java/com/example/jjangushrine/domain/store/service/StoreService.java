package com.example.jjangushrine.domain.store.service;

import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.service.SellerService;
import com.example.jjangushrine.domain.store.dto.request.StoreCreateReq;
import com.example.jjangushrine.domain.store.dto.request.StoreUpdateReq;
import com.example.jjangushrine.domain.store.dto.response.StoreRes;
import com.example.jjangushrine.domain.store.entity.Store;
import com.example.jjangushrine.domain.store.repository.StoreRepository;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.ConflictException;
import com.example.jjangushrine.exception.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final SellerService sellerService;

    @Transactional
    public void createStore(StoreCreateReq createReq, String email) {
        Seller seller = sellerService.findSellerByEmail(email);
        if (storeRepository.countBySellerAndIsDeletedFalse(seller)>0) {
            throw new ConflictException(ErrorCode.DUPLICATE_STORE);
        }
        Store store = createReq.to(seller);

        storeRepository.save(store);
    }

    public StoreRes getStore(String email) {
        Seller seller = sellerService.findSellerByEmail(email);
        Store store = storeRepository.findBySellerAndIsDeletedFalse(seller)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));

        return StoreRes.from(store);
    }

    @Transactional
    public StoreRes updateStore(StoreUpdateReq updateReq, String email) {
        Seller seller = sellerService.findSellerByEmail(email);
        Store store = storeRepository.findBySellerAndIsDeletedFalse(seller)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));

        store.update(updateReq);
        return StoreRes.from(store);
    }

    @Transactional
    public void deleteStore(String email) {
        Seller seller = sellerService.findSellerByEmail(email);
        Store store = storeRepository.findBySellerAndIsDeletedFalse(seller)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));

        store.softDelete();
    }
}
