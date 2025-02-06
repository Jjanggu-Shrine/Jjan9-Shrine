package com.example.jjangushrine.domain.rank.dto.response;

public record ProductRankRes(
	Long productId,
	Long viewCount,
	Short rank
) {
	public static ProductRankRes of (Long productId, Long viewCount, Short rank) {
		return new ProductRankRes(productId, viewCount, rank);
	}
}
