package com.schrodingdong.resourcemanager.repository;

import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Repository
public class ResourceRepository {
    private MinioClient minioClient;
    private final MinioRepository minioRepository;

    public ResourceRepository(MinioRepository minioRepository) {
        this.minioRepository = minioRepository;
    }
    @PostConstruct
    public void instantiateClient(){
        minioClient = minioRepository.getMinioInstance();
    }

    public void uploadResource(String fileName, String resBucketId, String bucketName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(resBucketId)
                        .filename(fileName)
                        .build()
        );
    }

    public void downloadResource(String objectName, String filename, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .overwrite(true)
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(filename)
                        .build()
        );
    }

    public void deleteResource(String objectName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }
}
