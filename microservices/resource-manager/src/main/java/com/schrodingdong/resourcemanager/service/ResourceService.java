package com.schrodingdong.resourcemanager.service;

import com.schrodingdong.resourcemanager.repository.ResourceRepository;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public void uploadObject(String fileName, String resBucketId, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.uploadFile(fileName,resBucketId,bucketName);
    }

    public void downloadObject(String objectName, String fileName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.downloadObject(objectName,fileName,bucketName);
    }

    public void deleteObject(String objectName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.deleteObject(objectName, bucketName);
    }
}
