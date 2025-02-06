package com.example.jjangushrine.domain.rank.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.example.jjangushrine.domain.rank.dto.response.ProductRankRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductRankService {

	private static final String PRODUCT_RANK_KEY = "productRank_"; // Key 값
	private static final String PRODUCT_RANK_VALUE = "productId_";
	private static final int RANKING_INCREMENT_SCORE = 1;
	private static final int RANKING_START = 0;
	private static final int RANKING_END = 9;

	private final RedisTemplate<String, String> redisTemplate;

	// 상품 조회수 증가
	public void increaseSearchCount(Long productId) {
		LocalDate today = LocalDate.now();

		for (int i=0; i < 3; i++) { // 현재부터 3일 간의 상품 조회수 저장
			String key = PRODUCT_RANK_KEY + today.plusDays(i).format(DateTimeFormatter.ISO_DATE); // yyyy-MM-dd 형식으로 지정

			redisTemplate.opsForZSet().incrementScore(key, PRODUCT_RANK_VALUE + productId.toString(), RANKING_INCREMENT_SCORE); // key : {member : score}
			redisTemplate.expire(key, 3, TimeUnit.DAYS); // 3일 동안만 유지

		}
	}

	public List<ProductRankRes> getProductRankList() {
		LocalDate today = LocalDate.now();
		String key = PRODUCT_RANK_KEY + today.format(DateTimeFormatter.ISO_DATE);
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet(); // ZSet 사용 (Sorted Set)
		Set<ZSetOperations.TypedTuple<String>> typedTuples = zSetOperations.reverseRangeWithScores(key, RANKING_START,
			RANKING_END); // 1 ~ 10위까지

		List<ZSetOperations.TypedTuple<String>> tupleList = new ArrayList<>(Objects.requireNonNull(typedTuples));

		return IntStream.range(0, tupleList.size())
			.mapToObj(index -> {
				ZSetOperations.TypedTuple<String> tuple = tupleList.get(index);
				String value = Objects.requireNonNull(tuple.getValue());
				Long productId = Long.parseLong(Objects.requireNonNull(value.substring(PRODUCT_RANK_VALUE.length())));
				Long viewCount = Objects.requireNonNull(tuple.getScore()).longValue();
				return ProductRankRes.of(productId, viewCount, (short) (index + 1));
			})
			.toList();
	}
}
