package com.example.jjangushrine.domain.order.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.jjangushrine.common.BaseEntity;
import com.example.jjangushrine.domain.order.enums.Status;
import com.example.jjangushrine.domain.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "staus", length = 15, nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "total_amount")
	private int totalAmount;

	@Column(name = "coupon_used")
	private boolean couponUsed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private List<OrderItem> orderItems = new ArrayList<>();



}
