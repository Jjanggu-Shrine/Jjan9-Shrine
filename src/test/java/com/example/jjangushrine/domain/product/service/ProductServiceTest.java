package com.example.jjangushrine.domain.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.jjangushrine.domain.product.dto.request.ProductSaveReq;
import com.example.jjangushrine.domain.product.dto.response.ProductRes;
import com.example.jjangushrine.domain.product.dto.request.ProductUpdateReq;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.store.entity.Store;
import com.example.jjangushrine.domain.store.repository.StoreRepository;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	ProductRepository productRepository;

	@Mock
	StoreRepository storeRepository; // productService 수정하면 같이 수정

	@Mock
	UserRepository userRepository; // productService 수정하면 같이 수정

	@InjectMocks
	ProductService productService;

	private ExecutorService executorService;
	private static final int THREAD_COUNT = 10;

	@Test
	@DisplayName("상품 등록 성공")
	void  saveProductSuccess() {
	    // given
		ProductSaveReq saveReq = createProductSaveReq();
		User mockSeller = createMockSeller();
		Store mockStore = createMockStore(mockSeller);
		Product mockProduct = createMockProduct(saveReq, mockStore);
		ProductRes expectedRes = ProductRes.fromEntity(mockProduct);

		given(storeRepository.findById(anyLong())).willReturn(Optional.of(mockStore));
		given(userRepository.findById(anyLong())).willReturn(Optional.of(mockSeller));
		given(productRepository.save(any(Product.class))).willReturn(mockProduct);

		// when
		ProductRes actualRes = productService.saveProduct(saveReq, mockSeller.getId());

	    // then
		assertEquals(expectedRes, actualRes);
		verify(productRepository).save(any(Product.class));
	}

	@Test
	@DisplayName("상품업데이트성공")
	void updateProductSeccess () {
	    // given
		ProductSaveReq saveReq = createProductSaveReq();
	    ProductUpdateReq updateReq = createProductUpdateReq();
		User mockSeller = createMockSeller();
		Store mockStore = createMockStore(mockSeller);
		Product mockProduct = createMockProduct(saveReq, mockStore);
		ProductRes expectedRes = ProductRes.fromEntity(mockProduct);

		given(productRepository.findById(anyLong())).willReturn(Optional.of(mockProduct));
		given(productRepository.existsByProductIdAndSellerId(anyLong(), anyLong())).willReturn(true);

	    // when
		ProductRes actualRes = productService.updateProduct(mockProduct.getId(), mockSeller.getId(), updateReq);

	    // then
		assertEquals(expectedRes, actualRes);
	}

	@Test
	@DisplayName("상품삭제성공")
	void deleteProductSuccess () {
		// given
		ProductSaveReq saveReq = createProductSaveReq();
		User mockSeller = createMockSeller();
		Store mockStore = createMockStore(mockSeller);
		Product mockProduct = createMockProduct(saveReq, mockStore);

		given(productRepository.findById(anyLong())).willReturn(Optional.of(mockProduct));
		given(productRepository.existsByProductIdAndSellerId(anyLong(), anyLong())).willReturn(true);

		// when
		productService.deleteProduct(mockSeller.getId(), mockProduct.getId());

		// then
		assertThat(mockProduct.getIsDeleted()).isTrue();
	}

	private User createMockSeller() {
		User mockSeller = User.builder().build();
		setField(mockSeller, "id", 1L);
		return mockSeller;
	}

	private Store createMockStore(User mockSeller) {
		Store mockStore = Store.builder().build();
		setField(mockStore, "id", 1L);
		setField(mockStore, "sellerId", mockSeller);
		return mockStore;
	}

	private Product createMockProduct(ProductSaveReq saveReq, Store mockStore) {
		Product mockProduct = Product.builder()
			.name(saveReq.name())
			.image(saveReq.image())
			.stock(saveReq.stock())
			.amount(saveReq.amount())
			.store(mockStore)
			.description(saveReq.description())
			.category(saveReq.category())
			.build();
		setField(mockProduct, "id", 1L);
		return mockProduct;
	}

	private ProductSaveReq createProductSaveReq() {
		return new ProductSaveReq(
			1L,
			"후드티A",
			100000,
			"짱구 후드티",
			"image",
			(short) 99,
			"TOP"
		);
	}

	private ProductUpdateReq createProductUpdateReq() {
		return new ProductUpdateReq(
			"후드티A",
			100000,
			"짱구 후드티",
			"image",
			(short) 99,
			"TOP"
		);
	}
}