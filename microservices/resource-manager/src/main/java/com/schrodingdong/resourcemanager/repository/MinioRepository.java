package com.schrodingdong.resourcemanager.repository;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinioRepository {
    private MinioClient minioClient = null;
    @Value("${minio.url}")
    private String minioUrl;
    @Value("${minio.username}")
    private String minioUsername;
    @Value("${minio.password}")
    private String minioPassword;


    public MinioClient getMinioInstance(){
        if(minioClient != null) return minioClient;
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername,minioPassword)
                .build();
        return minioClient;
    }

}
