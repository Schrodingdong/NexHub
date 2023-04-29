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
- Set up the containers with the compose file
   ````shell
   docker-compose up
   ````