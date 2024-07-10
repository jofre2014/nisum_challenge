# Build
FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/challenge*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
