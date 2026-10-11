# Use official Java 8 lightweight image
# FROM openjdk:8-jdk-alpine
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
# Copy the jar compiled by GitHub Actions
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
