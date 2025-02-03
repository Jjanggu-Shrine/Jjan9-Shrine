package com.example.jjangushrine.domain.user.controller;

import com.example.jjangushrine.common.ApiResMessage;
import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.user.dto.request.UserUpdateReq;
import com.example.jjangushrine.domain.user.dto.response.UserRes;
import com.example.jjangushrine.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserRes>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.GET_USER_SUCCESS,
                        userService.getUserInfo(userDetails.getEmail())));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<UserRes>> updateUser(
            @RequestBody UserUpdateReq userUpdateReq,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.UPDATE_USER_SUCCESS,
                        userService.updateUser(userUpdateReq, userDetails.getEmail())));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.deleteUser(userDetails.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(
                        ApiResMessage.DELETE_USER_SUCCESS));
    }
}
