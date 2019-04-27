FROM openjdk:8-alpine
WORKDIR /app
COPY build/libs libs/
COPY build/classes classes/
ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-Xmx2048m", "-cp", "/app/resources:/app/classes:/app/libs/*", "search.Search"]
EXPOSE 8080
