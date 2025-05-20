# Build Stage
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY . .
RUN mvn package

# Run Stage
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /app/target/ibike.jar app.jar
RUN adduser --disabled-password --gecos '' springuser
USER springuser
CMD ["java", "-jar", "app.jar"]