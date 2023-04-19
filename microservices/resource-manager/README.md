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