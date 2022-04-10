FROM mcr.microsoft.com/java/jdk:8-zulu-alpine
RUN addgroup -S spring && adduser -S spring -G spring
RUN chmod -R 777 /var/log
USER spring:spring
ARG JAR_FILE=target/*.jar
ARG PROPERTIES_FILE=src/main/resources/application-prod.yml
COPY ${JAR_FILE} app.jar
COPY ${PROPERTIES_FILE} application.yml
ENTRYPOINT ["java","-jar","/app.jar"]