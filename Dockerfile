# Stage 1: Build JAR
FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /app

# 1️⃣ Copy only pom.xml first (dependency layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2️⃣ Copy source code
COPY src ./src

# 3️⃣ Build JAR (won’t redownload deps if pom.xml unchanged)
RUN mvn clean package -DskipTests

# Stage 2: Run app
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]