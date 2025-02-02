package com.example.jjangushrine.domain.cart.service;

import static org.mockito.Mockito.*;

import com.example.jjangushrine.domain.cart.dto.request.CartItemCreateReq;
import com.example.jjangushrine.domain.cart.entity.Cart;
import com.example.jjangushrine.domain.cart.entity.CartItem;
import com.example.jjangushrine.domain.cart.repository.CartItemRepository;
import com.example.jjangushrine.domain.cart.repository.CartRepository;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    void addCartItem_shouldAddItemToCart() {
        //given
        User user = User.builder().build();
        Cart cart = new Cart(user);
        Product product = Product.builder()
                .name("아신")
                .amount(30000)
                .build();

        when(cartRepository.findById(user.getId())).thenReturn(Optional.of(cart));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // when
        CartItemCreateReq req = new CartItemCreateReq(user.getId(), product.getId(), 2);
        cartService.addCartItem(req);

        // then
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }
}