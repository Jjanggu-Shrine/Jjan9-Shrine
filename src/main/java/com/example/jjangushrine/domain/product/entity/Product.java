package com.example.jjangushrine.domain.product.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jjangushrine.common.BaseEntity;
import com.example.jjangushrine.domain.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", updatable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "stock", nullable = false)
	private Short stock;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Product(Store store,  String name, Integer amount, String description, Short stock) {
		this.store  = store;
		this.name = name;
		this.amount = amount;
		this.description = description;
		this.stock = stock;
		this.isDeleted = false;
	}
}
