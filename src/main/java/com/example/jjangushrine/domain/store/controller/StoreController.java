package com.example.jjangushrine.domain.store.controller;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.store.dto.request.StoreCreateReq;
import com.example.jjangushrine.domain.store.dto.request.StoreUpdateReq;
import com.example.jjangushrine.domain.store.dto.response.StoreRes;
import com.example.jjangushrine.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createStore(
            @Valid @RequestBody StoreCreateReq storeCreateReq,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        storeService.createStore(storeCreateReq, userDetails.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(ApiResMessage.CREATE_STORE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<StoreRes>> getStore(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.GET_STORE_SUCCESS,
                        storeService.getStore(userDetails.getEmail())));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<StoreRes>> updateStore(
            @Valid @RequestBody StoreUpdateReq storeUpdateReq,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.UPDATE_STORE_SUCCESS,
                        storeService.updateStore(storeUpdateReq, userDetails.getEmail())));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteStore(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        storeService.deleteStore(userDetails.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success( ApiResMessage.DELETE_STORE_SUCCESS));
    }
}
