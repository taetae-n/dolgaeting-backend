# 💜 덕질가이드: 돌개팅 — Backend

[덕질가이드: 돌개팅](https://dolgaeting.vercel.app) 프론트엔드를 위한 Spring Boot REST API 서버입니다.

> 프론트엔드 저장소: [github.com/taetae-n/dolgaeting](https://github.com/taetae-n/dolgaeting)
> 서비스 전체 소개 및 채점 엔진 구조는 프론트엔드 README를 참고해주세요.

---

## 🛠️ 기술 스택

- Java 21
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL (Neon)
- Docker (Render 배포용)
- Gradle

---

## 🗂️ 구조

```
src/main/java/com/example/demo/
├── Idol.java               # 아이돌 엔티티
├── IdolController.java     # 아이돌 CRUD + 성별 필터 + 태그 연결/해제
├── IdolRepository.java
│
├── Tag.java                 # 태그 엔티티
├── TagController.java       # 태그 CRUD
├── TagRepository.java
│
├── IdolTag.java             # 아이돌-태그 다대다 연결 엔티티
├── IdolTagRepository.java
│
├── GameSession.java         # 이용 기록 엔티티 (이름/MBTI/추천결과)
├── GameSessionController.java
├── GameSessionRepository.java
│
└── YoutubeController.java   # YouTube Data API 연동 (직캠 검색)

Dockerfile                   # Render 배포용 컨테이너 빌드 설정
```

---

## 📡 API 엔드포인트

### Idol
| Method | Endpoint | 설명 |
|---|---|---|
| GET | `/api/idols` | 전체 아이돌 조회 |
| GET | `/api/idols/gender/{gender}` | 성별 필터 조회 (M/F) |
| GET | `/api/idols/{id}/tags` | 특정 아이돌의 태그 조회 |
| POST | `/api/idols` | 아이돌 등록 |
| PUT | `/api/idols/{id}` | 아이돌 수정 |
| DELETE | `/api/idols/{id}` | 아이돌 삭제 |
| POST | `/api/idols/{idolId}/tags/{tagId}` | 태그 연결 |
| DELETE | `/api/idols/{idolId}/tags/{tagId}` | 태그 해제 |

### Tag
| Method | Endpoint | 설명 |
|---|---|---|
| GET | `/api/tags` | 전체 태그 조회 |
| POST | `/api/tags` | 태그 등록 |

### Session (이용 기록)
| Method | Endpoint | 설명 |
|---|---|---|
| GET | `/api/sessions` | 전체 이용 기록 조회 |
| POST | `/api/sessions` | 이용 기록 저장 |
| DELETE | `/api/sessions/{id}` | 개별 기록 삭제 |
| DELETE | `/api/sessions` | 전체 기록 삭제 |

### YouTube
| Method | Endpoint | 설명 |
|---|---|---|
| GET | `/api/youtube/search?query={query}` | 직캠 영상 검색 (상위 3개, YouTube Data API v3) |

---

## 🔐 환경변수

배포 환경(Render)에서는 아래 값을 환경변수로 주입받습니다. 민감 정보는 저장소에 포함되지 않습니다(`.gitignore` 처리).

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
youtube.api.key=${YOUTUBE_API_KEY}
```

로컬 개발 시에는 `application-local.properties`(Git 미포함)에 실제 값을 두고, `Active profiles: local`로 실행합니다.

---

## 🐳 배포

Render에서 Docker 기반으로 배포됩니다.

```dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build -x test
EXPOSE 8080
CMD ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
```

> 무료 플랜 사용으로 일정 시간 미사용 시 슬립 모드에 들어가며, 첫 요청 시 응답이 지연될 수 있습니다.

---

## 🌐 CORS

배포 환경(Vercel 프론트)에서의 요청을 허용하기 위해 전체 Controller에 `@CrossOrigin(origins = "*")`를 적용했습니다.

---

## 👤 만든 사람

타에현 ([@taetae-n](https://github.com/taetae-n))
