FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Maven build output (JAR file) into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot application runs on (default 8080)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
