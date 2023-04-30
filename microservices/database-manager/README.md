# How to start the service ?
To start the service, we have to follow these steps :
- Build the application
    ```
  ./mvnw package
    ```
- Create Docker Image for the app
    ```
  Docker build -t database-manager-service .
    ```
- Run the container
    ```
  Docker run -p9090:8080 --name db-service database-manager-service
    ```
  make sure to add the env variables :

  `NEO4J_USERNAME`, `NEO4J_PASSWORD` and `NEO4J_URI`
