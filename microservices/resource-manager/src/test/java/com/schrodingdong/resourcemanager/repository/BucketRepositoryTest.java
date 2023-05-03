package com.schrodingdong.resourcemanager.repository;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


class BucketRepositoryTest {

    private MinioRepository mockMinioRepository;
    private MinioClient mockMinioClient;
    private BucketRepository bucketRepositoryToTest;

    @BeforeEach
    void setUp() {
        mockMinioRepository = Mockito.mock(MinioRepository.class);
        mockMinioClient = Mockito.mock(MinioClient.class);
        bucketRepositoryToTest = new BucketRepository(mockMinioRepository);
        ReflectionTestUtils.setField(bucketRepositoryToTest, "minioClient", mockMinioClient);
    }

    @Test
    void postConstructTest() {
        bucketRepositoryToTest.instantiateClient();
        Mockito.verify(mockMinioRepository).getMinioInstance();
    }

    @Test
    void bucketExists() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean exists = bucketRepositoryToTest.bucketExists("testbucket");
        Mockito.verify(mockMinioClient).bucketExists(Mockito.any());
        Assertions.assertThat(exists).isFalse();
    }

    @Test
    void createBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketRepositoryToTest.createBucket("testbucket");
        Mockito.verify(mockMinioClient).makeBucket(Mockito.any());
    }

    @Test
    void removeBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketRepositoryToTest.removeBucket("testbucket");
        Mockito.verify(mockMinioClient).removeBucket(Mockito.any());
    }
}