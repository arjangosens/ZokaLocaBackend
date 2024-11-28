# Use the official Amazon Corretto 21 image as a parent image
FROM amazoncorretto:21

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and the build.gradle files
COPY gradlew build.gradle settings.gradle /app/
COPY gradle /app/gradle

# Grant execute permission for the Gradle wrapper
RUN chmod +x gradlew

# Copy the rest of the application code
COPY src /app/src

# Build the application
RUN ./gradlew build -x test

# Copy the built JAR file to the working directory using a wildcard
COPY build/libs/*.jar /app/ZokaLocaBackend.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "/app/ZokaLocaBackend.jar"]