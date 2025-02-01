package com.example.jjangushrine.domain.store.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import com.example.jjangushrine.common.BaseEntity;
import com.example.jjangushrine.domain.seller.entity.Seller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Store extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id")
	private Long id;

	@Column(nullable = false)
	private String businessNumber;

	@Column(nullable = false)
	private String businessName;

	@Column(nullable = false)
	private String storeName;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Short baseDeliveryFee;

	@Column
	@ColumnDefault("false")
	private Boolean isDeleted;

	@Column
	private LocalDateTime deletedAt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "seller_id")
	private Seller sellerId;

	public void softDelete() {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
	}
}
