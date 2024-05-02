# To build, run the following command from the top level project directory:
#
# docker build -f src/main/docker/Dockerfile .

FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]