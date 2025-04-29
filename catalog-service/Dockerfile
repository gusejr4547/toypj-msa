FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar CatalogService.jar

ENTRYPOINT ["java","-jar","CatalogService.jar"]

# ./gradlew clean build -x test