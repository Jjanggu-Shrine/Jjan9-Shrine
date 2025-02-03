package com.example.jjangushrine.domain.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.product.dto.request.ProductSaveReq;
import com.example.jjangushrine.domain.product.dto.response.ProductRes;
import com.example.jjangushrine.domain.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProductController {
	private final ProductService productService;

	@PostMapping("/sellers/products")
	public ResponseEntity<ApiResponse<ProductRes>> saveProduct(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Valid @RequestBody ProductSaveReq productSaveReq) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success("상품이 등록됐습니다.",
				productService.saveProduct(productSaveReq, userDetails.getId()))); // 1L 인증/인가 추가 시 수정 필요
	}

	@DeleteMapping("/sellers/products/{productId}")
	public ResponseEntity<ApiResponse<String>> deleteProduct(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long productId
	) {
		productService.deleteProduct(userDetails.getId(), productId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("상품 삭제에 성공했습니다."));
	}
}
