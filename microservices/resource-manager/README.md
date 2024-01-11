# How to use the service 
In order to use this service we need to initialise a MINIO docker image :
```
> docker run -p 9000:9000 -p 9090:9090 \
  --name minio1 \
  -v $HOME/minio/data:/data \
  -e "MINIO_ROOT_USER=minio" -e "MINIO_ROOT_PASSWORD=miniominio" \
  quay.io/minio/minio server /data \
  --console-address ":9090"
```

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

  `MINIO_USERNAME` and `MINIO_PASSWORD`

