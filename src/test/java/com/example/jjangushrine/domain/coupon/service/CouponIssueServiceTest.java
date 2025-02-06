package com.example.jjangushrine.domain.coupon.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.jjangushrine.domain.coupon.dto.request.CouponIssueReq;
import com.example.jjangushrine.domain.coupon.entity.Coupon;
import com.example.jjangushrine.domain.coupon.repository.CouponRepository;
import com.example.jjangushrine.domain.coupon.repository.UserCouponRepository;
import com.example.jjangushrine.domain.user.entity.User;
import com.example.jjangushrine.domain.user.enums.UserRole;
import com.example.jjangushrine.domain.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Slf4j
@SpringBootTest
class CouponIssueServiceTest {

	@Autowired
	private CouponIssueService couponIssueService;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UserCouponRepository userCouponRepository;

	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager em;

	@BeforeEach
	@Transactional
	void setUp() {
		userCouponRepository.deleteAll();
		couponRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("동시에 10000명이 쿠폰을 발급받으려 할 때 정확히 10개만 발급되어야 한다")
	void concurrentCouponIssueTest() throws InterruptedException {
		// given
		log.info("테스트 시작: 쿠폰과 사용자 생성");
		int threadCount = 10000;
		int couponQuantity = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);
		AtomicInteger successCount = new AtomicInteger();

		// 트랜잭션 내에서 테스트 데이터 생성
		Coupon coupon = transactionTemplate.execute(status -> {
			Coupon newCoupon = createTestCoupon(couponQuantity);
			log.info("테스트 쿠폰 생성 완료 - id: {}, totalQuantity: {}",
				newCoupon.getCouponId(), newCoupon.getTotalQuantity());
			return newCoupon;
		});

		List<User> users = transactionTemplate.execute(status -> {
			List<User> newUsers = new ArrayList<>();
			for (int i = 0; i < threadCount; i++) {
				User user = createTestUser("test" + i + "@test.com", "user" + i);
				newUsers.add(user);
				log.info("테스트 사용자 생성 완료 - id: {}, email: {}", user.getId(), user.getEmail());
			}
			return newUsers;
		});

		log.info("동시 요청 시작 - threadCount: {}, couponQuantity: {}", threadCount, couponQuantity);

		// when: 여러 스레드에서 동시에 쿠폰 발급 시도
		for (int i = 0; i < threadCount; i++) {
			final User user = users.get(i);
			executorService.submit(() -> {
				try {
					couponIssueService.issueCoupon(
						CouponIssueReq.of(user.getId(), coupon.getCouponId())
					);
					log.info("쿠폰 발급 성공 - userId: {}", user.getId());
					successCount.incrementAndGet();
				} catch (Exception e) {
					log.warn("쿠폰 발급 실패 - userId: {}, reason: {}", user.getId(), e.getMessage());
				} finally {
					latch.countDown();
				}
			});
		}

		// 모든 요청이 완료될 때까지 대기
		boolean completed = latch.await(10, TimeUnit.SECONDS);
		if (!completed) {
			log.error("일부 요청이 timeout으로 완료되지 못함");
		}

		executorService.shutdown();
		executorService.awaitTermination(5, TimeUnit.SECONDS);

		// then: 결과 검증
		log.info("결과 검증 시작");

		// 새로운 트랜잭션에서 결과 조회
		Coupon issuedCoupon = transactionTemplate.execute(status ->
			couponRepository.findById(coupon.getCouponId())
				.orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다."))
		);

		long actualIssuedCount = userCouponRepository.count();
		int successfulCount = successCount.get();

		log.info("검증 결과 - 발급된 쿠폰 수: {}, 성공 횟수: {}, 기대값: {}",
			actualIssuedCount, successfulCount, couponQuantity);

		assertThat(issuedCoupon.getUsedQuantity())
			.as("발급된 쿠폰 수가 총 수량과 일치해야 함")
			.isEqualTo(couponQuantity);
		assertThat(actualIssuedCount)
			.as("실제 발급된 수가 쿠폰 수량과 일치해야 함")
			.isEqualTo(couponQuantity);
		assertThat(successfulCount)
			.as("성공 횟수가 쿠폰 수량과 일치해야 함")
			.isEqualTo(couponQuantity);
	}

	@Autowired
	private TransactionTemplate transactionTemplate;  // 추가

	private Coupon createTestCoupon(int quantity) {
		return couponRepository.save(
			Coupon.builder()
				.name("테스트 쿠폰")
				.discountPercent(10)
				.minOrderAmount(10000)
				.validFrom(LocalDateTime.now())
				.validUntil(LocalDateTime.now().plusDays(7))
				.totalQuantity(quantity)
				.build()
		);
	}

	private User createTestUser(String email, String nickName) {
		return userRepository.save(
			User.builder()
				.email(email)
				.password("password")
				.nickName(nickName)
				.phoneNumber("01012341234")
				.build()
		);
	}
}