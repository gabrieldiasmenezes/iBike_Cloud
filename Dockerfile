# Build Stage
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom.xml para cache das dependências
COPY pom.xml .
RUN mvn dependency:resolve

# Copia o restante do código
COPY . .

# Build do jar sem rodar testes para agilizar (remova -DskipTests se quiser testar)
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copia o jar gerado
COPY --from=build /app/target/ibike.jar app.jar

# Cria usuário não-root para rodar o app (boa prática)
RUN adduser --disabled-password --gecos '' springuser
USER springuser

# Expõe porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação na porta 8080 e aceitar conexões externas
CMD ["java", "-jar", "app.jar", "--server.port=8080", "--server.address=0.0.0.0"]
