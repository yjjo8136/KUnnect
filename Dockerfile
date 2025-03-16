# JDK 17 기반 이미지 사용
FROM openjdk:17-alpine

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 빌드 후 생성된 JAR 파일 복사
COPY build/libs/*.jar app.jar

# 컨테이너에서 실행될 명령어 지정
CMD ["java", "-jar", "app.jar"]
