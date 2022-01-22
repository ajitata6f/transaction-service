FROM openjdk:8
LABEL maintainer='Adamu Abdulaziz'
VOLUME /tmp
ADD /build/libs/transaction-service-1.0.0.jar transaction-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]
