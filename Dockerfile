FROM openjdk:17
ARG JAR_FILE=target/gift_service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} gift_service.jar
ENTRYPOINT ["java", "-jar", "/gift_service.jar"]