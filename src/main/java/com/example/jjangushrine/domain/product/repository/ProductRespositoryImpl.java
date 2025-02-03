package com.example.jjangushrine.domain.product.repository;

import static com.example.jjangushrine.domain.product.entity.QProduct.*;
import static com.example.jjangushrine.domain.seller.entity.QSeller.*;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public class ProductRespositoryImpl implements ProductRepositoryCustom{

	private final JPAQueryFactory queryFactory;

	public ProductRespositoryImpl(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public boolean existsByProductIdAndSellerId(Long productId, Long sellerId) {
		return queryFactory
			.selectOne()
			.from(product)
			.join(product.store.sellerId, seller)
			.where(
				product.id.eq(productId),
				seller.id.eq(sellerId)
			)
			.fetchOne() != null;
	}
}
