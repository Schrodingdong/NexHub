package com.schrodingdong.resourcemanager.service;

import com.schrodingdong.resourcemanager.repository.BucketRepository;
import io.minio.errors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


class BucketServiceTest {

    private BucketService bucketServiceToTest;
    private BucketRepository bucketRepository;
    private String bucketId;
    @BeforeEach
    void setUp() {
        bucketRepository = Mockito.mock(BucketRepository.class);
        bucketServiceToTest = new BucketService(bucketRepository);
        bucketId = "bucketLmao";
    }

    @Test
    void createBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketServiceToTest.createBucket(bucketId);
        Mockito.verify(bucketRepository)
                .createBucket(bucketId);
    }

    @Test
    void deleteBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketServiceToTest.deleteBucket(bucketId);
        Mockito.verify(bucketRepository)
                .removeBucket(bucketId);
    }

    @Test
    void doesBucketExists() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketServiceToTest.doesBucketExists(bucketId);
        Mockito.verify(bucketRepository)
                .bucketExists(bucketId);
    }
}