package com.example.jjangushrine.domain.cart.entity;

import com.example.jjangushrine.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@RedisHash("cart") // Redis에 저장할 객체 명시
public class Cart {

	@Id
	private Long id;

	private Long userId; // Redis에서는 ID 필수
	private List<CartItem> cartItems = new ArrayList<>();


	public Cart(Long userId) {
		this.id = userId;
		this.userId = userId;
	}
}
