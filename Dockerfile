# Stage 1: Build
FROM gradle:7.6.0-jdk17 AS build

WORKDIR /app

# Copy Gradle Wrapper files
COPY gradlew .
COPY gradle/ gradle/

# Ensure Gradle Wrapper has the correct line endings and is executable
RUN apt-get update && apt-get install -y dos2unix
RUN dos2unix gradlew || true
RUN chmod +x gradlew

# Copy remaining project files
COPY build.gradle settings.gradle ./
COPY src ./src

# Debug: Verify all necessary files are present
RUN ls -la /app

# Use Gradle Wrapper to build the project
RUN ./gradlew build --no-daemon --info

# Stage 2: Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Set environment and expose port
ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


## Stage 1: Build
#FROM gradle:7.6.0-jdk17 AS build
#
#WORKDIR /app
#
## Copy Gradle Wrapper files
#COPY gradlew .
#COPY gradle/ gradle/
#
## Copy project files
#COPY build.gradle settings.gradle ./
#COPY src ./src
#
## Make Gradle Wrapper executable
#RUN chmod +x gradlew
#
## Use Gradle Wrapper to build the project
#RUN ./gradlew build --no-daemon --info
#
## Stage 2: Runtime
#FROM openjdk:17-jdk-slim
#
#WORKDIR /app
#COPY --from=build /app/build/libs/*.jar app.jar
#
#ENV SPRING_PROFILES_ACTIVE=docker
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
