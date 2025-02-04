package com.example.jjangushrine.domain.store.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import com.example.jjangushrine.common.BaseEntity;
import com.example.jjangushrine.domain.seller.entity.Seller;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "seller_id")
	private Seller seller;

	@Column(nullable = false, unique = true)
	private String businessNumber;

	@Column(nullable = false)
	private String businessName;

	@Column(nullable = false)
	private String storeName;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Short baseDeliveryFee;

	@Column(nullable = false)
	@ColumnDefault("false")
	private Boolean isDeleted = false;

	@Column
	private LocalDateTime deletedAt;

	@Builder
	public Store(
			Long id,
			Seller seller,
			String businessNumber,
			String businessName,
			String storeName,
			String description,
			Short baseDeliveryFee
	) {
		this.id = id;
		this.seller = seller;
		this.businessNumber = businessNumber;
		this.businessName = businessName;
		this.storeName = storeName;
		this.description = description;
		this.baseDeliveryFee = baseDeliveryFee;
	}

	public void softDelete() {
		this.isDeleted = true;
		this.deletedAt = LocalDateTime.now();
	}
}
