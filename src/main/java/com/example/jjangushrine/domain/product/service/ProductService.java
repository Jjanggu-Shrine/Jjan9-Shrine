package com.example.jjangushrine.domain.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jjangushrine.domain.product.dto.request.ProductSaveReq;
import com.example.jjangushrine.domain.product.dto.request.ProductUpdateReq;
import com.example.jjangushrine.domain.product.dto.response.ProductRes;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.enums.Category;
import com.example.jjangushrine.domain.seller.service.SellerService;
import com.example.jjangushrine.domain.store.service.StoreService;
import com.example.jjangushrine.exception.ErrorCode;
import com.example.jjangushrine.exception.common.NotFoundException;
import com.example.jjangushrine.exception.common.AccessDeniedException;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.store.entity.Store;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;
	private final StoreService storeService;
	private final SellerService sellerService;

	@Transactional
	public ProductRes saveProduct(ProductSaveReq productSaveReq, Long sellerId) {

		Store store = storeService.findStoreById(productSaveReq.storeId());

		if(!validateStoreAccessForSeller(store, sellerId)) {
			throw new AccessDeniedException(ErrorCode.STORE_FORBIDDEN_ACCESS);
		}

		Product product = productSaveReq.toEntity();
		product.registerStore(store);

		Product saveProduct = productRepository.save(product);
		return ProductRes.fromEntity(saveProduct);
	}

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

	public Page<ProductRes> getProductsByCategory(String category, Pageable pageable) {
		return productRepository.findAllProductByCategory(Category.valueOf(category), pageable);
	}

	public Page<ProductRes> getProductsByStoreWithCategoryFilter(Long storeId, String category, Pageable pageable) {
		return productRepository.findAllProductByStoreAndCategory(storeId, Category.valueOf(category), pageable);
	}

	public boolean validateStoreAccessForSeller(Store store, Long sellerId) {

		Seller seller = sellerService.findSellerById(sellerId);

		if (seller != store.getSeller()) {
			return false;
		}

		return true;
	}

	public Product getProductById(Long productId) {
		Product findProduct = productRepository.findById(productId)
			.orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

		findProduct.validateIsDeleted();
		return findProduct;
	}

	public void validateProductOwnedBySeller(Long productId, Long sellerId) {
		if(!productRepository.existsByProductIdAndSellerId(productId, sellerId)) {
			throw new AccessDeniedException(ErrorCode.PRODUCT_FORBIDDEN_ACCESS);
		}
	}

	@Transactional
	public void decreaseStock(Long productId, int quantity) {
		Product product = getProductById(productId);
		product.decreaseStock(quantity);
		productRepository.save(product);
	}

	@Transactional
	public void increaseStock(Long productId, int quantity) {
		Product product = getProductById(productId);
		product.increaseStock(quantity);
		productRepository.save(product);
	}
}
