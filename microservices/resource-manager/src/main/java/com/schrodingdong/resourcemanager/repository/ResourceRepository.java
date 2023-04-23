package com.schrodingdong.resourcemanager.repository;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Repository
public class ResourceRepository {
    private MinioClient minioClient;
    public ResourceRepository() {
        minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minio","miniominio")
                .build();
    }
    public void uploadFile(String fileName, String resBucketId, String bucketName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(resBucketId)
                        .filename(fileName)
                        .build());
    }

    public void downloadObject(String objectName, String resPath, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(resPath)
                        .build()
        );
    }

    public void deleteObject(String objectName, String buckerName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(buckerName)
                        .object(objectName)
                        .build()
        );
    }
}
