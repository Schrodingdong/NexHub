package com.schrodingdong.resourcemanager.repository;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;


public class MinioRepository {
    protected MinioClient minioClient = null;
    @Value("${minio.url}")
    private String minioUrl;
    @Value("${minio.username}")
    private String minioUsername;
    @Value("${minio.password}")
    private String minioPassword;
    public void instanciateClient(){
        if(minioClient != null) return;
        minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername,minioPassword)
                .build();
    }
}
