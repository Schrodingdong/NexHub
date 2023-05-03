package com.schrodingdong.resourcemanager.repository;

import io.minio.MinioClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Disabled because it requires a running minio instance
 * */
@Disabled
class MinioRepositoryTest {
    private MinioRepository minioRepositoryToTest;

    @BeforeEach
    void setUp(){
        minioRepositoryToTest = new MinioRepository();
    }

    @Test
    void getMinioInstance(){
        ReflectionTestUtils.setField(minioRepositoryToTest, "minioUrl", "http://localhost:9000");
        ReflectionTestUtils.setField(minioRepositoryToTest, "minioUsername", "minio");
        ReflectionTestUtils.setField(minioRepositoryToTest, "minioPassword", "miniominio");
        MinioClient minioInstance = minioRepositoryToTest.getMinioInstance();
        Assertions.assertThat(minioInstance).isInstanceOf(MinioClient.class);
    }
}