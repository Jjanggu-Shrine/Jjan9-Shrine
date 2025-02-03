package com.example.jjangushrine.domain.seller.controller;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.seller.dto.request.SellerUpdateReq;
import com.example.jjangushrine.domain.seller.dto.response.SellerRes;
import com.example.jjangushrine.domain.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<ApiResponse<SellerRes>> getSellerInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.GET_SELLER_SUCCESS,
                        sellerService.getSellerInfo(userDetails.getEmail())));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<SellerRes>> updateUser(
            @RequestBody SellerUpdateReq updateReq,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.UPDATE_SELLER_SUCCESS,
                        sellerService.updateUser(updateReq, userDetails.getEmail())));
    }
}
