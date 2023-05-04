package com.example.authentification;

import com.example.authentification.appuser.MetadataUserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Random;

class MetadataUserModelTest {

    private MetadataUserModel metadataUserModel;

    @BeforeEach
    void setUp() {
        metadataUserModel = new MetadataUserModel("testuser", "testuser@example.com");
    }

    @Test
    void testGenerateBucketIdViaUserMail() {
        String userMail = metadataUserModel.getMail();

        // Mock the random byte array
        byte[] randomBytes = {1, 2, 3, 4, 5};
        Random random = new Random();
        random.nextBytes(randomBytes);

        String expectedBucketId = generateBucketId(userMail, randomBytes);

        // call the method
        String bucketId = metadataUserModel.generateBucketIdViaUserMail(userMail);

        // Assert the result
        Assertions.assertNotEquals(expectedBucketId, bucketId);
    }

    private String generateBucketId(String userMail, byte[] randomBytes) {
        String firstPartOfMail = userMail.substring(0,userMail.indexOf('@'));

        // Salt generation
        String salt = new String(randomBytes, Charset.forName("UTF-8"));

        // result string
        String result = userMail+salt;
        result = firstPartOfMail+"-"+Math.abs(result.hashCode());
        return result.toLowerCase();
    }
}
