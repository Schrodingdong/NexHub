docker build -t authentication-service ./microservices/authentification
docker build -t metadata-db-manager-service ./microservices/database-manager
docker build -t resource-manager-service ./microservices/resource-manager
docker build -t gateway-service ./microservices/gateway-service
docker build -t discovery-service ./microservices/discovery-service
