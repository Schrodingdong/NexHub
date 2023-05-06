package com.schrodingdong.resourcemanager.repository;

import io.minio.*;
import io.minio.errors.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Repository
@NoArgsConstructor
public class ResourceRepository extends MinioRepository{

    public void uploadResource(String fileName, String resBucketId, String bucketName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(resBucketId)
                        .filename(fileName)
                        .build());
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
