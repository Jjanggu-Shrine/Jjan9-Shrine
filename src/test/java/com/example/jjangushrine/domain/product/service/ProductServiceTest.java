package com.example.jjangushrine.domain.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.jjangushrine.domain.product.dto.request.ProductSaveReq;
import com.example.jjangushrine.domain.product.dto.response.ProductSaveRes;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.seller.entity.Seller;
import com.example.jjangushrine.domain.seller.repository.SellerRepository;
import com.example.jjangushrine.domain.store.entity.Store;
import com.example.jjangushrine.domain.store.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	ProductRepository productRepository;

	@Mock
	StoreRepository storeRepository; // productService 수정하면 같이 수정

	@Mock
	SellerRepository sellerRepository; // productService 수정하면 같이 수정

	@InjectMocks
	ProductService productService;

	@Test
	@DisplayName("상품 등록 성공")
	void  saveProductSuccess() {
	    // given
		ProductSaveReq saveReq = createProductSaveReq();
		Seller mockSeller = createMockSeller();
		Store mockStore = createMockStore(mockSeller);
		Product mockProduct = createMockProduct(saveReq, mockStore);
		ProductSaveRes expectedRes = ProductSaveRes.fromEntity(mockProduct);

		given(storeRepository.findById(anyLong())).willReturn(Optional.of(mockStore));
		given(sellerRepository.findById(anyLong())).willReturn(Optional.of(mockSeller));
		given(productService.validateStoreAccessForSeller(mockStore, eq(mockSeller.getId()))).willReturn(true);


	    // when
		ProductSaveRes actualRes = productService.saveProduct(saveReq, mockSeller.getId());

	    // then
		assertEquals(expectedRes, actualRes);
		verify(productRepository).save(any(Product.class));
	}

	private Seller createMockSeller() {
		Seller mockSeller = Seller.builder().build();
		setField(mockSeller, "id", 1L);
		return mockSeller;
	}

	private Store createMockStore(Seller mockSeller) {
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

}
