FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY backend/pom.xml backend/
COPY backend/src backend/src

RUN mvn -f backend/pom.xml clean package


FROM eclipse-temurin:21-jre

COPY --from=builder /app/backend/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]