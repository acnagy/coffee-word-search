FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY /build/libs/coffee-word-search-1.2.0.jar /app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "-Dspring.profiles.active=production", "/app.jar"]
EXPOSE 8080
