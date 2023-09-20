FROM openjdk:11.0.9.1-oraclelinux8
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
VOLUME ["/var/log"]
ENTRYPOINT ["java","-jar","/app.jar"]