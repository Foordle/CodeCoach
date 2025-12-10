# 1. 빌드 단계 (Gradle 빌드)
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY . .

# gradlew에 실행 권한 부여
RUN chmod +x gradlew

# 빌드 수행 (테스트 제외하여 속도 향상)
RUN ./gradlew bootJar -x test

# 2. 실행 단계
FROM eclipse-temurin:21-jdk
WORKDIR /app

# [중요] 내부에서 docker 명령어를 써야 하므로 Docker Client 설치
RUN apt-get update && apt-get install -y docker.io && rm -rf /var/lib/apt/lists/*

# 빌드된 Jar 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# .env 파일 복사 (API 키)
COPY .env .env

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]