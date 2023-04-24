# How to initiate the service
to start the service, we follow these steps :
- build the application
    ```shell
    > ./mvnw package
    ```
- Create Docker image of the jar
    ```shell
    > docker build -t authentication-service .
    ```
- Start the services with `docker-compose` file
    ```shell
    > docker-compose up
    ```