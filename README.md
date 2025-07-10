# VYBZ Follow Service

VYBZ 플랫폼의 팔로우 기능을 담당하는 마이크로서비스입니다.

## 📋 목차

-   [개요](#개요)
-   [기술 스택](#기술-스택)
-   [주요 기능](#주요-기능)
-   [프로젝트 구조](#프로젝트-구조)
-   [API 문서](#api-문서)
-   [설치 및 실행](#설치-및-실행)
-   [환경 설정](#환경-설정)
-   [아키텍처](#아키텍처)
-   [개발 가이드](#개발-가이드)

## 🎯 개요

VYBZ Follow Service는 다음과 같은 기능을 제공합니다:

-   **팔로우 관리**: 사용자와 버스커 간의 팔로우 관계 관리
-   **팔로워/팔로잉 조회**: 사용자별 팔로잉 목록, 버스커별 팔로워 목록 조회
-   **팔로우 상태 확인**: 사용자의 특정 버스커 팔로우 여부 확인
-   **API 제공**: 팔로우 관련 REST API 제공
-   **서비스 디스커버리**: Eureka Client를 통한 서비스 등록
-   **API 문서화**: Swagger를 통한 API 문서 제공
-   **이벤트 기반 통신**: Kafka를 통한 비동기 이벤트 처리

## 🛠 기술 스택

### Backend

![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)

### Infra

![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)
![Amazon EC2](https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

### 협업

![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

### Database

-   **MongoDB**: 팔로우 관계 데이터 저장 (Read Model)
-   **MySQL**: 팔로우 관계 데이터 저장 (Write Model)

### Message Queue

-   **Apache Kafka**: 비동기 이벤트 처리 및 서비스 간 통신

### Documentation

-   **Swagger/OpenAPI 3.0**: API 문서화

### Build & Deploy

-   **Gradle**: 빌드 도구
-   **Docker**: 컨테이너화

## 🚀 주요 기능

### 1. 팔로우 관리

-   **팔로우 추가**: 사용자가 버스커를 팔로우
-   **팔로우 삭제**: 사용자가 버스커 언팔로우
-   **팔로우 상태 확인**: 특정 사용자의 버스커 팔로우 여부 확인

### 2. 팔로워/팔로잉 조회

-   **팔로잉 목록 조회**: 사용자가 팔로우하는 버스커 목록 조회
-   **팔로워 목록 조회**: 버스커를 팔로우하는 사용자 목록 조회
-   **커서 기반 페이징**: 효율적인 페이징 처리

### 3. 사용자 정보 동기화

-   **팔로워 정보 업데이트**: 사용자 정보 변경 시 팔로워 목록 업데이트
-   **팔로잉 정보 업데이트**: 버스커 정보 변경 시 팔로잉 목록 업데이트

### 4. 이벤트 기반 통신

-   **Kafka Producer**: 팔로우 이벤트 발행
-   **Kafka Consumer**: 사용자 정보 변경 이벤트 수신
-   **비동기 처리**: 이벤트 기반 비동기 데이터 처리
-   **이벤트 타입**:
    -   FollowEvent (팔로우 생성)
    -   UnfollowEvent (팔로우 삭제)
    -   FollowerEvent (팔로워 정보 업데이트)
    -   FollowingEvent (팔로잉 정보 업데이트)

### 5. API 제공

-   **RESTful API**: 표준 REST API 제공
-   **응답 표준화**: 통일된 응답 형식 제공
-   **예외 처리**: 체계적인 예외 처리

### 6. 서비스 디스커버리

-   **Eureka Client**: 마이크로서비스 디스커버리에 등록
-   **서비스 등록**: 자동 서비스 등록 및 헬스체크

## 📁 프로젝트 구조

```
src/main/java/com/vybz/follow_service/
├── follow/                    # 팔로우 도메인
│   ├── application/           # 팔로우 서비스 로직
│   │   ├── FollowService.java
│   │   └── FollowServiceImpl.java
│   ├── domain/                # 팔로우 도메인 모델
│   │   ├── Follow.java
│   │   ├── Follower.java
│   │   └── Following.java
│   ├── dto/                   # 팔로우 DTO
│   │   ├── request/
│   │   │   ├── RequestAddFollowDto.java
│   │   │   ├── RequestDeleteFollowDto.java
│   │   │   ├── RequestUpdateFollowerDto.java
│   │   │   └── RequestUpdateFollowingDto.java
│   │   └── response/
│   │       ├── ResponseBuskerFollowerDto.java
│   │       └── ResponseUserFollowingDto.java
│   ├── infrastructure/        # 팔로우 리포지토리
│   │   ├── FollowRepository.java
│   │   ├── FollowRepositoryCustom.java
│   │   └── FollowRepositoryCustomImpl.java
│   ├── presentation/          # 팔로우 컨트롤러
│   │   └── FollowController.java
│   └── vo/                    # 팔로우 VO
│       ├── request/
│       │   ├── RequestAddFollowVo.java
│       │   ├── RequestDeleteFollowVo.java
│       │   ├── RequestUpdateFollowerVo.java
│       │   └── RequestUpdateFollowingVo.java
│       └── response/
│           ├── ResponseBuskerFollowerVo.java
│           └── ResponseUserFollowingVo.java
├── common/                    # 공통 모듈
│   ├── config/                # 설정 클래스들
│   │   ├── MongoConfig.java
│   │   └── SwaggerConfig.java
│   ├── entity/                # 공통 엔티티
│   │   ├── BaseEntity.java
│   │   ├── BaseResponseEntity.java
│   │   ├── BaseResponseStatus.java
│   │   └── SoftDeletableEntity.java
│   ├── exception/             # 예외 처리
│   │   ├── AsyncExceptionHandler.java
│   │   ├── BaseException.java
│   │   ├── BaseExceptionHandler.java
│   │   └── BaseExceptionHandlerFilter.java
│   └── util/                  # 유틸리티
│       ├── CursorPageUtil.java
│       └── MongoCursorPageHelper.java
├── kafka/                     # Kafka 관련 모듈
│   ├── config/                # Kafka 설정
│   │   ├── CommonKafkaConfig.java
│   │   ├── FollowerKafkaConfig.java
│   │   ├── FollowingKafkaConfig.java
│   │   ├── FollowKafkaConfig.java
│   │   └── UnfollowKafkaConfig.java
│   ├── consumer/              # Kafka Consumer
│   │   ├── DeleteFollowerEventConsumer.java
│   │   ├── DeleteFollowingEventConsumer.java
│   │   ├── UpdateFollowerEventConsumer.java
│   │   └── UpdateFollowingEventConsumer.java
│   ├── event/                 # Kafka 이벤트
│   │   ├── FollowerEvent.java
│   │   ├── FollowEvent.java
│   │   ├── FollowingEvent.java
│   │   └── UnfollowEvent.java
│   └── producer/              # Kafka Producer
│       ├── FollowKafkaProducer.java
│       └── UnfollowKafkaProducer.java
└── FollowServiceApplication.java
```

## 📚 API 문서

Swagger UI를 통해 API 문서를 확인할 수 있습니다:

-   **URL**: `http://localhost:8000/follow-service/swagger-ui.html`
-   **API 그룹**: Follow-Service

### 주요 API 엔드포인트

#### 팔로우 관리 API

-   `POST /api/v1/follow` - 팔로우 추가
-   `DELETE /api/v1/follow` - 팔로우 삭제
-   `GET /api/v1/follow/check` - 팔로우 여부 확인

#### 팔로잉/팔로워 조회 API

-   `GET /api/v1/follow/following-list` - 사용자 팔로잉 목록 조회
-   `GET /api/v1/follow/follower-list` - 버스커 팔로워 목록 조회

#### 정보 업데이트 API

-   `PUT /api/v1/follow/follower` - 팔로워 정보 업데이트
-   `PUT /api/v1/follow/following` - 팔로잉 정보 업데이트

### API 요청/응답 예시

#### 팔로우 추가 요청

```json
{
    "userUuid": "user-123",
    "buskerUuid": "busker-456"
}
```

#### 팔로잉 목록 조회 응답

```json
{
    "status": "SUCCESS",
    "message": "요청이 성공적으로 처리되었습니다.",
    "data": {
        "content": [
            {
                "buskerUuid": "busker-456",
                "nickname": "스트리트뮤지션",
                "profileImageUrl": "https://example.com/profile.jpg"
            }
        ],
        "hasNext": true,
        "lastId": "last-cursor-id"
    }
}
```

## 🚀 설치 및 실행

### 1. 사전 요구사항

-   Java 17
-   Gradle 8.4+
-   Docker (선택사항)
-   MongoDB
-   MySQL
-   Apache Kafka

### 2. 로컬 실행

```bash
# 프로젝트 클론
git clone <repository-url>
cd vybz-follow-service

# Gradle 빌드
./gradlew clean build

# 애플리케이션 실행
./gradlew bootRun
```

### 3. Docker 실행

```bash
# Docker 이미지 빌드
docker build -t vybz-follow-service .

# Docker 컨테이너 실행
docker run -p 8000:8000 vybz-follow-service
```

## ⚙️ 환경 설정

### 주요 설정 파일

-   `application.yml`: 기본 설정
-   `application-dev.yml`: 개발 환경 설정

### 환경 변수

```yaml
# MongoDB 설정
spring:
  data:
    mongodb:
      uri: mongodb://${MONGO_HOST}:${MONGO_PORT}/${MONGO_DATABASE}

# MySQL 설정
spring:
  datasource:
    url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}
    username: ${MYSQL_USERNAME}
    password: ${MYSQL_PASSWORD}

# Kafka 설정
spring:
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS}
```

## 🏗️ 아키텍처

### Hexagonal Architecture (Clean Architecture)

-   **Domain Layer**: 팔로우 도메인 모델과 비즈니스 로직
-   **Application Layer**: 팔로우 서비스 로직과 유스케이스
-   **Infrastructure Layer**: MongoDB/MySQL 접근과 외부 시스템 연동
-   **Presentation Layer**: REST API 엔드포인트

### 마이크로서비스 패턴

-   **Service Discovery**: Eureka Client를 통한 서비스 등록
-   **Stateless**: 상태 없는 서비스 설계
-   **API Gateway**: 통합 API 게이트웨이 연동

### 이벤트 기반 아키텍처

-   **Kafka Producer**: 팔로우 관련 이벤트 발행
-   **Kafka Consumer**: 다른 서비스의 이벤트 수신 및 처리
-   **비동기 통신**: 서비스 간 느슨한 결합
-   **이벤트 타입**:
    -   FollowEvent (팔로우 생성)
    -   UnfollowEvent (팔로우 삭제)
    -   FollowerEvent (팔로워 정보 업데이트)
    -   FollowingEvent (팔로잉 정보 업데이트)

### 데이터베이스 설계

-   **MongoDB**: 팔로우 관계 Read Model 저장
-   **MySQL**: 팔로우 관계 Write Model 저장
-   **Auditing**: 생성/수정 시간 자동 관리
-   **인덱스**: 성능 최적화를 위한 인덱스 설정

### 공통 모듈

-   **BaseResponseEntity**: 통일된 응답 형식
-   **BaseException**: 체계적인 예외 처리
-   **SwaggerConfig**: API 문서화 설정
-   **MongoConfig**: MongoDB 설정
-   **CursorPageUtil**: 커서 기반 페이징 유틸리티

## 🔧 개발 가이드

### 코드 컨벤션

-   **패키지 구조**: 도메인별 계층 분리
-   **네이밍**: 명확하고 일관된 네이밍 규칙
-   **예외 처리**: BaseException을 통한 통일된 예외 처리
-   **로깅**: Slf4j를 통한 구조화된 로깅

### DTO/VO 패턴

-   **DTO**: 내부 서비스 간 데이터 전송
-   **VO**: 외부 API 요청/응답 데이터
-   **변환 메서드**: DTO ↔ VO 변환 메서드 제공

### Kafka 이벤트 처리

-   **Producer**: 팔로우 관련 이벤트 발행
-   **Consumer**: 다른 서비스의 이벤트 수신 및 처리
-   **이벤트 타입**: 다양한 팔로우 관련 이벤트
-   **비동기 처리**: 이벤트 기반 비동기 데이터 처리

### 페이징 처리

-   **커서 기반 페이징**: 효율적인 대용량 데이터 처리
-   **CursorPageUtil**: 커서 기반 페이징 유틸리티
-   **MongoCursorPageHelper**: MongoDB 커서 페이징 헬퍼

### 테스트

```bash
# 단위 테스트 실행
./gradlew test

# 통합 테스트 실행
./gradlew integrationTest
```

### 빌드

```bash
# Gradle 빌드
./gradlew clean build

# JAR 파일 생성
./gradlew bootJar
```

빌드된 JAR 파일은 `build/libs/` 디렉토리에 생성됩니다.

## 📝 라이선스

이 프로젝트는 VYBZ 팀의 내부 프로젝트입니다.

## 👥 팀

-   **개발팀**: VYBZ Backend Team

---

**VYBZ Follow Service** - 효율적인 팔로우 관계 관리 서비스
