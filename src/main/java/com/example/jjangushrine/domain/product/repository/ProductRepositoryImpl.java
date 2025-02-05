package com.example.jjangushrine.domain.product.repository;

import static com.example.jjangushrine.domain.product.entity.QProduct.product;
import static com.example.jjangushrine.domain.seller.entity.QSeller.seller;
import static com.example.jjangushrine.domain.store.entity.QStore.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.example.jjangushrine.domain.product.dto.response.ProductRes;
import com.example.jjangushrine.domain.product.enums.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
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

	@Override
	public Page<ProductRes> findAllProductByCategory(Category category, Pageable pageable) {
		List<ProductRes> content = queryFactory.select(Projections.constructor(ProductRes.class,
				product.id,
				product.store.id,
				product.name,
				product.amount,
				product.description,
				product.image,
				product.stock,
				product.category
			))
			.from(product)
			.where(product.category.eq(category), product.isDeleted.isFalse())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> queryCount = queryFactory
			.select(product.count())
			.from(product)
			.where(product.category.eq(category), product.isDeleted.isFalse());

		return PageableExecutionUtils.getPage(content, pageable, queryCount::fetchOne);
	}

	@Override
	public Page<ProductRes> findAllProductByStoreAndCategory(Long storeId, Category category, Pageable pageable) {
		List<ProductRes> content = queryFactory.select(Projections.constructor(ProductRes.class,
				product.id,
				product.store.id,
				product.name,
				product.amount,
				product.description,
				product.image,
				product.stock,
				product.category
			))
			.from(product)
			.join(product.store, store)
			.where(product.category.eq(category), product.store.id.eq(storeId), product.isDeleted.isFalse())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> queryCount = queryFactory
			.select(product.count())
			.from(product)
			.where(product.store.id.eq(storeId), product.category.eq(category), product.isDeleted.isFalse());

		return PageableExecutionUtils.getPage(content, pageable, queryCount::fetchOne);
	}
}
