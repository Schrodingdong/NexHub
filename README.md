# NexHub - your resource sharing hub !

This repository is for the J2EE project using the following technologies
- Spring
- React
- Microservices
- Neo4j

## Explaining file structure

- Each microservice sits alone in a folder
- When doing changes to a specific service, be sure to push from the root (the folder with the services folder)

## Building the project
to build the microservices, be in the root folder of the project, and run the command: 
- Build each microservice :
   ```shell
   ./mvnw package -DskipTests
   ```
- Create Docker Images for each microservice
   ````shell
   ./create-images.bat
   ````
- Set up the containers with the compose file. 
   ````shell
   docker-compose up
   ````
  Note : Don't forget to add a `.env` file with the following variables :
  - AUTH_DB_USERNAME
  - AUTH_DB_PASSWORD
  - AUTH_DB_NAME
  - AUTH_DB_URL
  - MINIO_URL
  - MINIO_USERNAME
  - MINIO_PASSWORD
  - NEO4J_USERNAME
  - NEO4J_PASSWORD
  - NEO4J_URI

## Accessing the services
We will be using the Gateway-service to access all the services. to get services name, goto `localhost:8761` to get to Eureka homepage. To access a specific service, get its name and use it in the url **with lower case**

example :
```
service name : METADATA-DB-MANAGER-SERVICE
url to access it : http://localhost:8080/metadata-db-manager-service/some-endpoint....
```