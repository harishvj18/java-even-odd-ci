# Multi-stage build: compile/test with Maven, then run on a small Java runtime.
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn --batch-mode --no-transfer-progress clean verify

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/even-odd-1.0.0.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
