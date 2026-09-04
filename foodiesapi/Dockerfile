FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime environment
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy the compiled jar from the build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Memory limitation added so Spring Boot doesn't crash on Render's free tier
ENTRYPOINT ["java", "-Xmx300m", "-jar", "app.jar"]
