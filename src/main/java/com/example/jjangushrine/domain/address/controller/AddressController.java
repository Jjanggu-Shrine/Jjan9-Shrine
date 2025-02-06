package com.example.jjangushrine.domain.address.controller;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.address.dto.request.AddressCreateReq;
import com.example.jjangushrine.domain.address.dto.request.AddressPageRequest;
import com.example.jjangushrine.domain.address.dto.request.AddressUpdateReq;
import com.example.jjangushrine.domain.address.dto.response.AddressListRes;
import com.example.jjangushrine.domain.address.dto.response.AddressRes;
import com.example.jjangushrine.domain.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createAddress(
            @Valid @RequestBody AddressCreateReq createReq,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        addressService.createAddress(createReq, userDetails.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(ApiResMessage.CREATE_ADDRESS_SUCCESS));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressRes>> getAddressInfo(
            @PathVariable Long addressId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.GET_ADDRESS_SUCCESS,
                        addressService.getAddressInfo(addressId, userDetails.getEmail()
                        )));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AddressListRes>> getAddressList(
            @ModelAttribute AddressPageRequest pageRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.GET_ADDRESS_SUCCESS,
                        addressService.getAddressList(pageRequest.toPageRequest(),
                                userDetails.getEmail()))
                );
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressRes>> updateAddress(
            @Valid @RequestBody AddressUpdateReq updateReq,
            @PathVariable Long addressId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.UPDATE_ADDRESS_SUCCESS,
                        addressService.updateAddress(
                                updateReq, addressId, userDetails.getEmail()))
                );
    }

    @PatchMapping("/{addressId}/default")
    public ResponseEntity<ApiResponse<AddressRes>> setDefaultAddress(
            @PathVariable Long addressId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        addressService.setDefaultAddress(addressId, userDetails.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(ApiResMessage.SET_DEFAULT_ADDRESS_SUCCESS)
                );
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long addressId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        addressService.deleteAddress(addressId, userDetails.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(ApiResMessage.DELETE_ADDRESS_SUCCESS));
    }
}
