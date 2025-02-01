package com.example.jjangushrine.domain.cart.service;

import com.example.jjangushrine.domain.cart.dto.request.CartItemCreateReq;
import com.example.jjangushrine.domain.cart.dto.response.CartItemCreateRes;
import com.example.jjangushrine.domain.cart.entity.Cart;
import com.example.jjangushrine.domain.cart.entity.CartItem;
import com.example.jjangushrine.domain.cart.repository.CartItemRepository;
import com.example.jjangushrine.domain.cart.repository.CartRepository;
import com.example.jjangushrine.domain.product.entity.Product;
import com.example.jjangushrine.domain.product.repository.ProductRepository;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartItemCreateRes addCartItem(CartItemCreateReq reqDto) {

        // 장바구니 가져오기
        Cart cart = cartRepository.findById(reqDto.getUserId()).
                orElseGet(() -> createCartForUser(reqDto.getUserId()));

        // 상품 담기
        Product product = productRepository.findById(reqDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        int quantity = reqDto.getQuantity();
        int totalPrice = product.getAmount() * quantity;

        CartItem cartItem = new CartItem(cart, product, quantity);
        cartItemRepository.save(cartItem);

        return new CartItemCreateRes(
                product.getId(),
                product.getName(),
                cartItem.getQuantity(),
                totalPrice
        );
    }

    // 상품이 존재하지 않으면 장바구니 자동생성 메소드
    private Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        Cart newCart = new Cart(user);
        return cartRepository.save(newCart);
    }
}
