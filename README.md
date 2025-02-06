# Jjan9-Shrine

# 짱구신사 (JjangGu Shop) 🛍️

## 📝 프로젝트 소개

`짱구신사`는 안정적이고 확장 가능한 이커머스 플랫폼을 목표로 개발된 쇼핑몰 프로젝트입니다. MSA 아키텍처를 기반으로 회원, 상품, 주문, 쿠폰 등 쇼핑몰의 핵심 도메인을 구현했습니다.

## 🌟 주요 기능

### 👥 회원/인증 시스템 (Member & Authentication)
- **회원 관리**
  - 일반 회원/판매자 회원 CRUD
  - JWT 기반 인증/인가
  - 스토어 CRUD
- **소셜 로그인**
  - OAuth2.0 기반 소셜 로그인 구현
  - 네이버 로그인 지원

### 🛍️ 상품 관리 & 캐싱 (Product & Caching)
- **상품 시스템**
  - 상품 CRUD
  - 상품 검색 
  - INDEX를 활용하여 검색 기능 최적화
- **캐싱 시스템**
  - 실시간 인기 상품 랭킹 (TOP 10) 

### 🛒 주문/결제 시스템 (Order & Payment)
- **장바구니**
  - Redis 기반 장바구니 CRUD
  - 재고 동시성 제어
- **주문/결제**
  - 주문/주문 취소

### 🎫 쿠폰 & 인프라 (Coupon & Infrastructure)
- **쿠폰 시스템**
  - 쿠폰 발급/사용
  - 동시성 제어
- **CI/CD**
  - GitHub Actions
  - AWS CodeDeploy

## 🛠 기술 스택

### Backend
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

### Database
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">

### Infrastructure
<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">

## 📊 시스템 아키텍처

![Image](https://github.com/user-attachments/assets/2c9c7b12-e5f8-4b12-95cc-560924f44a8a)

```mermaid
graph TD
    A[Client] --> B[API Gateway]
    B --> C[Member Service]
    B --> D[Product Service]
    B --> E[Order Service]
    B --> F[Coupon Service]
    C --> G[(MySQL)]
    D --> G[(MySQL)]
    E --> G[(MySQL)]
    F --> G[(MySQL)]
    C --> H[(Redis)]
    D --> H[(Redis)]
```

## 📋 ERD

[ERD 이미지]

## 🚀 성능 개선

### 1. 캐싱 전략
- **적용 포인트**
  - 실시간 상품 랭킹 (Redis Sorted Set)
  - 장바구니 (Redis Hash)

### 2. 동시성 제어
- **분산적 락 (Optimistic Lock)**
  - 쿠폰 수량 관리
    
- **비관적 락 (Pessimistic Lock)**
  - 주문 생성 시 재고 차감관리
  - 주문 취소 시 재고 증가관리

### 3. 대용량 데이터 조회 최적화
- **Indexing**
  - 상품 검색 속도 향상

## 🔍 트러블슈팅

 🐞 [상품 조회 속도 저하로 인한 개선](https://github.com/Jjanggu-Shrine/Jjan9-Shrine/issues/69#issue-2832016451)

## 📝 API 문서
- [Swagger UI](링크)


## 👨‍👩‍👧‍👦 팀원 소개

| 이름 | 담당 | GitHub |
|------|------|--------|
| 고예나 | 회원/인증 | [@yeana]([GitHub 링크](https://github.com/goo3oo?tab=repositories)) |
| 이하영 | 상품/캐싱 | [@hayoung]([GitHub 링크](https://github.com/duol9)) |
| 이동건 | 주문 | [@donggeon]([GitHub 링크](https://github.com/LeeDong-gun)) |
| 이정우 | 쿠폰/인프라 | [@jeongwoo]([GitHub 링크](https://github.com/wldnr1208)) |

## 🏃‍♂️ 시작하기

```bash
# 레포지토리 클론
git clone https://github.com/your-repository/jjanggu-shop.git

# 디렉토리 이동
cd jjanggu-shop

# 필요한 패키지 설치
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

---
