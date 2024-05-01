FROM maven:3-amazoncorretto-21
ADD init.sql /docker-entrypoint-initdb.d
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
