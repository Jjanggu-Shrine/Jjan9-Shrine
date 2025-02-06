package com.example.jjangushrine.domain.coupon.service;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LockService {
	//분산락 구현
	private final RedisTemplate<String, String> redisTemplate;
	private static final String LOCK_PREFIX = "LOCK:";

	// Lock을 획득하고 비즈니스 로직을 실행하는 메인 메서드
	public <T> T executeWithLock(String key, long timeoutMs, long waitMs, Supplier<T> callback) {
		String lockKey = LOCK_PREFIX + key;  // Lock의 key 생성
		String randomValue = UUID.randomUUID().toString();  // Lock의 value로 사용할 고유 값

		while (true) {
			// 1. Lock 획득 시도
			boolean acquired = acquireLock(lockKey, randomValue, timeoutMs);
			if (acquired) {
				log.info("Lock acquired - key: {}, value: {}", lockKey, randomValue);
				try {
					// 2. Lock 획득 성공시 비즈니스 로직 실행
					return callback.get();
				} finally {
					// 3. 비즈니스 로직 완료 후 Lock 해제
					releaseLock(lockKey, randomValue);
					log.info("Lock released - key: {}, value: {}", lockKey, randomValue);
				}
			}

			// 4. Lock 획득 실패시 재시도 로직
			log.debug("Lock acquisition failed, waiting to retry - key: {}", lockKey);
			try {
				Thread.sleep(waitMs);  // 일정 시간 대기 후 재시도
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Lock 획득 대기 중 인터럽트 발생", e);
			}
		}
	}

	// Redis에 Lock을 설정하는 메서드
	private boolean acquireLock(String key, String value, long timeoutMs) {
		return Boolean.TRUE.equals(
			redisTemplate.opsForValue()
				.setIfAbsent(key, value, Duration.ofMillis(timeoutMs))  // SET NX + EXPIRE 명령어
		);
	}

	// Redis에서 Lock을 해제하는 메서드
	private void releaseLock(String key, String value) {
		// Lua 스크립트를 사용하여 원자적으로 Lock 해제
		String script =
			"if redis.call('get', KEYS[1]) == ARGV[1] then " +
				"return redis.call('del', KEYS[1]) else return 0 end";

		redisTemplate.execute(
			new DefaultRedisScript<>(script, Long.class),
			Collections.singletonList(key),
			value
		);
	}
}