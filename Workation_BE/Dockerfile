# syntax=docker/dockerfile:1

# --- Build stage: compile and package the Spring Boot app with the Gradle wrapper ---
FROM eclipse-temurin:26-jdk AS build
WORKDIR /app

# Cache dependencies: copy the wrapper + build files first, resolve, then copy sources.
COPY gradle/ gradle/
COPY gradlew settings.gradle.kts build.gradle.kts ./
RUN chmod +x gradlew && ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

COPY src ./src
RUN ./gradlew --no-daemon clean bootJar -x test

# --- Runtime stage: run the fat jar on a slim JRE ---
FROM eclipse-temurin:26-jre AS runtime
WORKDIR /app

# Run as a non-root user.
RUN useradd --system --create-home appuser
USER appuser

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
