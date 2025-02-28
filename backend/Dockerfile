# Jar deploy
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml mvnw ./
COPY src ./src
COPY .mvn ./.mvn
RUN chmod +x mvnw && ./mvnw package -DskipTests

# Running
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/NoteScheduleAppBackend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]