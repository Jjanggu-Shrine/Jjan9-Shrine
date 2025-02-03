package com.example.jjangushrine.domain.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jjangushrine.domain.product.dto.request.ProductSaveReq;
import com.example.jjangushrine.domain.product.dto.response.ProductRes;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.exception.common.StoreAccessDeniedException;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.repository.SellerRepository;
import com.example.jjangushrine.domain.store.entity.Store;
import com.example.jjangushrine.domain.store.repository.StoreRepository;
import com.example.jjangushrine.exception.common.StoreNotFoundException;
import com.example.jjangushrine.exception.common.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository; // 추후 Service를 참조하도록 변경하기
	private final SellerRepository sellerRepository; // 마찬가지

	@Transactional
	public ProductRes saveProduct(ProductSaveReq productSaveReq, Long sellerId) {

		// storeService에 해당 로직 메서드 생기면 변경 예정
		Store store = storeRepository.findById(productSaveReq.storeId())
			.orElseThrow(StoreNotFoundException::new);

		if(!validateStoreAccessForSeller(store, sellerId)) {
			throw new StoreAccessDeniedException(); // Store에서 관련된 예외처리 생성 시 그걸로 변경하기
		}

		Product product = productSaveReq.toEntity();
		product.registerStore(store);

		Product saveProduct = productRepository.save(product);
		return ProductRes.fromEntity(saveProduct);
	}

	protected boolean validateStoreAccessForSeller(Store store, Long sellerId) {

		// sellerService에 해당 로직 메서드 생기면 변경 예정 + Execption 종류도
		Seller seller = sellerRepository.findById(sellerId)
			.orElseThrow(UserNotFoundException::new);

		if (seller != store.getSellerId()) {
			return false;
		}

		return true;
	}
}
