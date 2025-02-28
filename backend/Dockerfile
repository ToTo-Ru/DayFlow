FROM openjdk:21-jdk-slim
WORKDIR /app
COPY . .
RUN ./mvnw package
EXPOSE $PORT
CMD ["java", "-jar", "target/NoteScheduleAppBackend-0.0.1-SNAPSHOT.jar"]