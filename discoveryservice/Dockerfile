FROM openjdk:17-ea-jdk-slim
VOLUME /tmp
COPY build/libs/discoveryservice-0.0.1-SNAPSHOT.jar DiscorveryService.jar

ENTRYPOINT ["java","-jar","DiscorveryService.jar"]