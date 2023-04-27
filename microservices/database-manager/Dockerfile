FROM openjdk:latest
EXPOSE 8082
ADD target/database-manager-1.0.jar database-manager-1.0.jar
ENTRYPOINT ["java","-jar","/database-manager-1.0.jar"]