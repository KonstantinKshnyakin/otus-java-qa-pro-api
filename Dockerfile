FROM maven:3.9.6-eclipse-temurin-17-alpine
WORKDIR /api-tests
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
ENTRYPOINT ["mvn", "test"]