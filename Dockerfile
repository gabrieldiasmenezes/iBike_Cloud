# Stage 1 - Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# Stage 2 - Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Variáveis de ambiente padrão (podem ser sobrescritas no run)
ENV DB_HOST=postgres \
    DB_USER=postgres \
    DB_PASSWORD=postgres \
    DB_NAME=appdb

EXPOSE 8080

RUN adduser -D appuser
USER appuser

CMD ["java", "-jar", "app.jar"]

