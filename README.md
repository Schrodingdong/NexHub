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

## Paths
| Service                     | Url                                                                                | http method | body                                                                                                                           |
|-----------------------------|------------------------------------------------------------------------------------|-------------|--------------------------------------------------------------------------------------------------------------------------------|
| metadata-db-manager-service | /res/get/all                                                                       | get         |                                                                                                                                |
| metadata-db-manager-service | /res/get/all-from-user/{userId}                                                    | get         |                                                                                                                                |
| metadata-db-manager-service | /res/get/public-from-user/{userId}                                                 | get         |                                                                                                                                |
| metadata-db-manager-service | /res/get/from-res-name/{name}                                                      | get         |                                                                                                                                |
| metadata-db-manager-service | /res/get/{resId}                                                                   | get         |                                                                                                                                |
| metadata-db-manager-service | /res/add/{userId}                                                                  | post        | ```{ "resourceName" : "xxxx", "resourceDescription":"xxxx", "resourceVisibility":"xxxx", "resourceBucketId":"xxxx-0000..."}``` |
| metadata-db-manager-service | /res/update/{resId}                                                                | put         |                                                                                                                                |
| metadata-db-manager-service | /res/delete/all                                                                    | delete      |                                                                                                                                |
| metadata-db-manager-service | /res/delete/all-of-user/{userId}                                                   | delete      |                                                                                                                                |
| metadata-db-manager-service | /res/delete/{resId}                                                                | delete      |                                                                                                                                |
| metadata-db-manager-service | /users/get/all                                                                     | get         |                                                                                                                                |
| metadata-db-manager-service | /users/get/id/{userId}                                                             | get         |                                                                                                                                |
| metadata-db-manager-service | /users/get/mail/{mail}                                                             | get         |                                                                                                                                |
| metadata-db-manager-service | /users/get/username/{username}                                                     | get         |                                                                                                                                |
| metadata-db-manager-service | /users/get/{userId}/followers                                                      | get         |                                                                                                                                |
| metadata-db-manager-service | /users/get/{userId}/following                                                      | get         |                                                                                                                                |
| metadata-db-manager-service | /users/add                                                                         | post        | ```{"username" : "xx", "firstName" : "xx", "lastName" :  "xx", "email" : "xx@xx.xx", "bucketId" : "alllowercase-0000000.."}``` |
| metadata-db-manager-service | /users/update/id/{userId}                                                          | put         | same as post but add only the needed fields **without email and bucketID**                                                     |
| metadata-db-manager-service | /users/{userId}/follows/{followId}                                                 | put         |                                                                                                                                |
| metadata-db-manager-service | /users/delete/all                                                                  | delete      |                                                                                                                                |
| metadata-db-manager-service | /users/delete/id/{userId}                                                          | delete      |                                                                                                                                | 
| resource-manager-service    | /buckets/exists/{bucketId}                                                         | get         |                                                                                                                                | 
| resource-manager-service    | /buckets/create/{bucketId}                                                         | post        |                                                                                                                                | 
| resource-manager-service    | /buckets/delete/{bucketId}                                                         | delete      |                                                                                                                                | 
| resource-manager-service    | /resource/upload?userMail=xx@xx.xx&resourceDescription=xxxx&resourceVisibility=xxx | post        | add a multipart file with param name : "file"                                                                                  | 
| resource-manager-service    | /resource/download?resourceId=xxx&userMail=xx@xx.xx&downloadName=xxxx              | get         |                                                                                                                                | 
| resource-manager-service    | /resource/delete?resourceId=xxx&userMail=xx                                        | delete      |                                                                                                                                | 
| authentication-service      | /api/v1/registration                                                               | post        | ```{"firstName":"xx", "lastName":"xx", "email:"xx@xx.xx", "password":"xx"}```                                                  | 
| authentication-service      | /login                                                                             | post        | ```{"email":"xx@xx.xx", "password":"xx"}```                                                                                    | 
| authentication-service      | /token                                                                             | post        | ```{"email":"xx@xx.xx", "password":"xx"}```                                                                                    | 
| authentication-service      | /validateToken?token=xxxxxxx                                                       | get         |                                                                                                                                | 

