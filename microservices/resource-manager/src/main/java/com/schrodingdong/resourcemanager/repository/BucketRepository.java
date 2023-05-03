package com.schrodingdong.resourcemanager.repository;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Repository
public class BucketRepository {
    private MinioClient minioClient;
    private final MinioRepository minioRepository;

    public BucketRepository(MinioRepository minioRepository) {
        this.minioRepository = minioRepository;
    }
    @PostConstruct
    public void instantiateClient(){
        minioClient = minioRepository.getMinioInstance();
    }

    public boolean bucketExists(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );
        return bucketExists;
    }

    public void createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
    }

    public void removeBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeBucket(
                RemoveBucketArgs.builder().bucket(bucketName).build()
        );
    }
}
