FROM openjdk:latest
EXPOSE 8761
ADD target/discovery-service-1.0.jar discovery-service-1.0.jar
ENTRYPOINT [ "java", "-jar", "discovery-service-1.0.jar" ]