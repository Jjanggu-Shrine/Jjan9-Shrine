package com.example.jjangushrine.domain.order.service;

import com.example.jjangushrine.config.security.entity.CustomUserDetails;
import com.example.jjangushrine.domain.order.dto.response.OrderRes;
import com.example.jjangushrine.domain.order.entity.Order;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.service.UserService;
import com.example.jjangushrine.exception.common.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OderService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserService userService;


    public OrderRes createOrder(CustomUserDetails authUser, Long couponId) {
        String userId = authUser.getId().toString();
        String cartKey = "cart:" + userId;

        Map<Object, Object> cartItems = redisTemplate.opsForHash().entries(cartKey);

        if (cartItems.isEmpty() || (cartItems.size() == 1 && cartItems.containsKey("init"))) {
            throw new OrderNotFoundException();
        }

        User user = userService.findUserByEmail(authUser.getEmail());

        Order order = Order.builder()
                .user(user)
                .couponUsed( > 0)

    }
}
