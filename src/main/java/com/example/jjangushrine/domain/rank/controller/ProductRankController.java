package com.example.jjangushrine.domain.rank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jjangushrine.common.ApiResponse;
import com.example.jjangushrine.domain.rank.dto.response.ProductRankRes;
import com.example.jjangushrine.domain.rank.service.ProductRankService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProductRankController {

	private final ProductRankService productRankService;

	@GetMapping("/products/ranking")
	public ResponseEntity<ApiResponse<List<ProductRankRes>>> getProductRankList() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(ApiResponse.success("인기 상품 랭킹 조회에 성공했습니다.", productRankService.getProductRankList()));
	}
}
