# Shared runtime image for every Spring Boot service in this repo.
# The jar is built beforehand (by CI or `./mvnw package`), then copied in:
#   docker build --build-arg SERVICE=order-service -t order-service .
FROM eclipse-temurin:25-jre

ARG SERVICE
WORKDIR /app

RUN groupadd --system app && useradd --system --gid app app
COPY ${SERVICE}/target/*.jar app.jar
USER app

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]
