package com.example.jjangushrine.domain.cart.service;

import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.cart.dto.request.CartItemCreateReq;
import com.example.jjangushrine.domain.cart.dto.response.CartItemCreateRes;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.exception.common.LockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RedissonClient redissonClient;

    @Mock
    private RLock rLock;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private CartService cartService;

    private CustomUserDetails authUser;

    @BeforeEach
    void setup() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .build();
        authUser = new CustomUserDetails(user);

        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(redissonClient.getLock(anyString())).thenReturn(rLock);
    }

    @Test
    void addCartItem_정상추가() throws InterruptedException {
        // Given
        Long productId = 1L;
        int quantity = 2;
        int productPrice = 30000;

        Product product = Product.builder()
                .name("테스트 상품")
                .amount(productPrice)
                .build();
        ReflectionTestUtils.setField(product, "id", productId);

        CartItemCreateReq req = new CartItemCreateReq(productId, quantity);

        String cartKey = "cart:" + authUser.getId();
        String productKey = "product:" + productId;

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(hashOperations.get(cartKey, productKey)).thenReturn(3);
        when(rLock.tryLock(5, 10, TimeUnit.SECONDS)).thenReturn(true);

        // when
        CartItemCreateRes res = cartService.addCartItem(authUser, req);

        // Then
        assertThat(res.cartId()).isEqualTo(cartKey);
        assertThat(res.productId()).isEqualTo(productId);
        assertThat(res.quantity()).isEqualTo(5);
        assertThat(res.totalPrice()).isEqualTo(5 * productPrice);

        verify(productRepository, times(1)).findById(productId);
        verify(hashOperations, times(1)).get(cartKey, productKey);
        verify(hashOperations, times(1)).put(cartKey, productKey, 5);
        verify(rLock, times(1)).unlock();
    }

    @Test
    void addCartItem_상품이_없으면_예외발생() throws InterruptedException {
        // Given
        Long productId = 2L;
        CartItemCreateReq req = new CartItemCreateReq(productId, 2);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        when(rLock.tryLock(5, 10, TimeUnit.SECONDS)).thenReturn(true);

        // Then
        assertThatThrownBy(() -> cartService.addCartItem(authUser, req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 상품");

        verify(rLock, times(1)).unlock();
    }

    @Test
    void addCartItem_락을_얻지못하면_예외발생() throws InterruptedException {
        // Given
        Long productId = 2L;
        CartItemCreateReq req = new CartItemCreateReq(productId, 2);

        when(rLock.tryLock(5, 10, TimeUnit.SECONDS)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> cartService.addCartItem(authUser, req))
                .isInstanceOf(LockException.class)
                .hasMessageContaining("다른 요청이 처리중입니다");

        verify(rLock, never()).unlock();
    }
}