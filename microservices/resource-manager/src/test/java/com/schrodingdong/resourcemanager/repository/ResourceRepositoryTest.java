package com.schrodingdong.resourcemanager.repository;

import ch.qos.logback.core.testUtil.MockInitialContext;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class ResourceRepositoryTest {

    private MinioRepository mockMinioRepository;
    private MinioClient mockMinioClient;
    private ResourceRepository resourceRepositoryToTest;
    @BeforeEach
    void setUp(){
        mockMinioRepository = Mockito.mock(MinioRepository.class);
        mockMinioClient = Mockito.mock(MinioClient.class);
        resourceRepositoryToTest = new ResourceRepository(mockMinioRepository);
        ReflectionTestUtils.setField(resourceRepositoryToTest, "minioClient", mockMinioClient);
    }

    @Test
    void postConstructTest(){
        resourceRepositoryToTest.instantiateClient();
        Mockito.verify(mockMinioRepository).getMinioInstance();
    }

    @Test
    void uploadResource() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Bypass the regulatory checks
        File fileMock = Mockito.mock(File.class);
        Mockito.when(fileMock.getAbsolutePath()).thenReturn("/path/to/test.txt");
        Mockito.mockStatic(Files.class);
        Mockito.when(Files.isRegularFile(
                Mockito.eq(Paths.get(fileMock.getAbsolutePath())),
                Mockito.any()
        )).thenReturn(true);
        // Test the method
        resourceRepositoryToTest.uploadResource(
                fileMock.getAbsolutePath(),
                "resBucketId",
                "bucketname"
        );
        // Verify that the method was called
        Mockito.verify(mockMinioClient)
                .uploadObject(Mockito.any());
    }

    @Test
    void downloadResource() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepositoryToTest.downloadResource("objectName", "filename", "bucketname");
        Mockito.verify(mockMinioClient)
                .downloadObject(Mockito.any());
    }

    @Test
    void deleteResource() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepositoryToTest.deleteResource("objectName", "bucketname");
        Mockito.verify(mockMinioClient)
                .removeObject(Mockito.any());
    }
}