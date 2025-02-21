# build >> jenkins를 사용하면 거기서 빌드가 가능하다고 함.
# 수동으로 ./gradlew build 수행후 진행할것.
#FROM gradle:jdk17 AS builder
#WORKDIR /app
#COPY . .
#RUN ./gradlew clean build -x test
# run stage에서 COPY --from=builder /app/build/libs/*.jar app.jar 이거 하면 builder에서 jar 복사 가능하다


# run
FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/user-service-0.0.1-SNAPSHOT.jar UserService.jar

ENTRYPOINT ["java","-jar","UserService.jar"]
