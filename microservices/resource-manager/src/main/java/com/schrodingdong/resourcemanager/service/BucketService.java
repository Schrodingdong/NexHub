package com.schrodingdong.resourcemanager.service;

import com.schrodingdong.resourcemanager.repository.BucketRepository;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class BucketService implements IBucketService{
    private final BucketRepository bucketRepository;

    public BucketService(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    @Override
    public void createBucket(String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketRepository.createBucket(bucketId);
    }

    @Override
    public void deleteBucket(String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketRepository.removeBucket(bucketId);
    }

    @Override
    public boolean doesBucketExists(String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return bucketRepository.bucketExists(bucketId);
    }

}
