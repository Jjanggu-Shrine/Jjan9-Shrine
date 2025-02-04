package com.example.jjangushrine.domain.product.repository;

import static com.example.jjangushrine.domain.product.entity.QProduct.product;
import static com.example.jjangushrine.domain.seller.entity.QSeller.seller;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	public ProductRepositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public boolean existsByProductIdAndSellerId(Long productId, Long sellerId) {
		return queryFactory
			.selectOne()
			.from(product)
			.join(product.store.seller, seller)
			.where(
				product.id.eq(productId),
				seller.id.eq(sellerId)
			)
			.fetchOne() != null;
	}
}
