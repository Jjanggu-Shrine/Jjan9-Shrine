package com.example.jjangushrine.domain.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProductController {

	private final ProductService productService;

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long productId
	) {
		productService.deleteProduct(userDetails.getId(), productId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null));
	}
}
