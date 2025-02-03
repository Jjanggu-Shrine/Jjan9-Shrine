package com.example.jjangushrine.domain.product.service;

import java.beans.FeatureDescriptor;
import java.util.Arrays;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jjangushrine.domain.product.dto.request.ProductUpdateReq;
import com.example.jjangushrine.domain.product.dto.response.ProductRes;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.exception.ProductAccessDeniedException;
import com.example.jjangushrine.domain.product.exception.ProductNotFoundException;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.seller.repository.SellerRepository;
import com.example.jjangushrine.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;
	private final StoreRepository storeRepository; // 추후 Service를 참조하도록 변경하기
	private final SellerRepository sellerRepository; // 마찬가지

	@Transactional
	public ProductRes updateProduct(Long productId, Long sellerId, ProductUpdateReq updateReq) {

		Product findProduct = getProductById(productId);
		validateProductOwnedBySeller(productId, sellerId);

		findProduct.update(updateReq);

		return ProductRes.fromEntity(findProduct);
	}

	@Transactional
	public void deleteProduct(Long sellerId, Long productId) {

		Product findProduct = getProductById(productId);
		validateProductOwnedBySeller(productId, sellerId);

		findProduct.delete();
	}

	public Product getProductById(Long productId) {
		Product findProduct = productRepository.findById(productId)
			.orElseThrow(ProductNotFoundException::new);

		findProduct.validateIsDeleted();
		return findProduct;
	}

	protected void validateProductOwnedBySeller(Long productId, Long sellerId) {
		if(!productRepository.existsByProductIdAndSellerId(productId, sellerId)) {
			throw new ProductAccessDeniedException();
		}
	}
}
