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
	private final RedisTemplate<String, String> redisTemplate;
	private static final String LOCK_PREFIX = "LOCK:";

	public <T> T executeWithLock(String key, long timeoutMs, long waitMs, Supplier<T> callback) {
		String lockKey = LOCK_PREFIX + key;
		String randomValue = UUID.randomUUID().toString();

		while (true) {
			boolean acquired = acquireLock(lockKey, randomValue, timeoutMs);
			if (acquired) {
				log.info("Lock acquired - key: {}, value: {}", lockKey, randomValue);
				try {
					return callback.get();
				} finally {
					releaseLock(lockKey, randomValue);
					log.info("Lock released - key: {}, value: {}", lockKey, randomValue);
				}
			}

			log.debug("Lock acquisition failed, waiting to retry - key: {}", lockKey);
			try {
				Thread.sleep(waitMs);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Lock 획득 대기 중 인터럽트 발생", e);
			}
		}
	}

	private boolean acquireLock(String key, String value, long timeoutMs) {
		return Boolean.TRUE.equals(
			redisTemplate.opsForValue()
				.setIfAbsent(key, value, Duration.ofMillis(timeoutMs))
		);
	}

	private void releaseLock(String key, String value) {
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
