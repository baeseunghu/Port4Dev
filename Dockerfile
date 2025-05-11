# Step 1: Gradle로 빌드하는 단계 (빌드 컨테이너)
FROM gradle:7.6.1-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN ./gradlew build

# Step 2: 실제 실행하는 단계 (실행 컨테이너)
FROM openjdk:17-jdk-slim
COPY --from=build /home/gradle/project/build/libs/Port4Dev-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]