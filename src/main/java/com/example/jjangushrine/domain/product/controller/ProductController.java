package com.example.jjangushrine.domain.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.domain.product.dto.request.ProductSaveReq;
import com.example.jjangushrine.domain.product.dto.response.ProductSaveRes;
import com.example.jjangushrine.domain.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping("/sellers/products")
	public ResponseEntity<ApiResponse<ProductSaveRes>> saveProduct(@Valid @RequestBody ProductSaveReq productSaveReq) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success("상품이 등록됐습니다.", productService.saveProduct(productSaveReq, 1L))); // 1L 인증/인가 추가 시 수정 필요
	}
}
