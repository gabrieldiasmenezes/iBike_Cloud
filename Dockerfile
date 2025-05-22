# Build Stage
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:resolve

COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copia qualquer .jar gerado e renomeia para app.jar
COPY --from=build /app/target/*.jar app.jar

# Cria usuário não-root (boa prática)
RUN adduser --disabled-password --gecos '' springuser

# Cria o diretório /data e dá permissão ao usuário springuser
RUN mkdir -p /data && chown -R springuser:springuser /data

# Muda para o usuário não-root
USER springuser

# Expõe porta padrão do Spring Boot
EXPOSE 8080

# Roda a aplicação
CMD ["java", "-jar", "app.jar"]