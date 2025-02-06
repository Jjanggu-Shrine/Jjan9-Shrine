package com.example.jjangushrine.domain.store.service;

import com.example.jjangushrine.domain.store.dto.request.StoreCreateReq;
import com.example.jjangushrine.domain.store.dto.request.StoreUpdateReq;
import com.example.jjangushrine.domain.store.dto.response.StoreRes;
import com.example.jjangushrine.domain.store.entity.Store;
import com.example.jjangushrine.domain.store.repository.StoreRepository;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.service.UserService;
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
    private final UserService userService;

    @Transactional
    public void createStore(StoreCreateReq createReq, String email) {
        User user = userService.findUserByEmail(email);
        if (storeRepository.existsByBusinessNumberAndIsDeletedIsFalse(
                createReq.businessNumber())
        ) {
            throw new ConflictException(ErrorCode.DUPLICATE_BUSINESS_NUMBER);
        }

        if (storeRepository.countByUserAndIsDeletedIsFalse(user)>0) {
            throw new ConflictException(ErrorCode.DUPLICATE_STORE);
        }
        Store store = createReq.to(user);

        storeRepository.save(store);
    }

    public StoreRes getStore(String email) {
        User user = userService.findUserByEmail(email);
        Store store = storeRepository.findByUserAndIsDeletedIsFalse(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));

        return StoreRes.from(store);
    }

    @Transactional
    public StoreRes updateStore(StoreUpdateReq updateReq, String email) {
        User user = userService.findUserByEmail(email);
        Store store = storeRepository.findByUserAndIsDeletedIsFalse(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));

        store.update(updateReq);
        return StoreRes.from(store);
    }

    @Transactional
    public void deleteStore(String email) {
        User user = userService.findUserByEmail(email);
        Store store = storeRepository.findByUserAndIsDeletedIsFalse(user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));

        store.softDelete();
    }

    public Store findStoreById(long id) {
        return storeRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.STORE_NOT_FOUND));
    }
}
