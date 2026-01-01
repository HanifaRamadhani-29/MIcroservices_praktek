# Tahap 1: Build (Menggunakan Maven untuk membuat file JAR)
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Tahap 2: Run (Menjalankan file JAR yang sudah jadi)
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
# Kita ambil file .jar dari tahap build di atas
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]