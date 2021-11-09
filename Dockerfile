FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} oauth2-google-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/oauth2-google-0.0.1-SNAPSHOT.jar"]