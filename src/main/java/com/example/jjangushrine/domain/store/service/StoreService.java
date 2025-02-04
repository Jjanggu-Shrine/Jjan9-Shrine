package com.example.jjangushrine.domain.store.service;

import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.service.SellerService;
import com.example.jjangushrine.domain.store.dto.request.StoreCreateReq;
import com.example.jjangushrine.domain.store.entity.Store;
import com.example.jjangushrine.domain.store.repository.StoreRepository;
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
        Store store = createReq.to(seller);
        storeRepository.save(store);
    }
}
