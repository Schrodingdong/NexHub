package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.service.BucketService;
import io.minio.errors.*;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/buckets")
@CrossOrigin
public class BucketController {
    @Autowired
    private BucketService bucketService;

    @PostMapping("/create/{bucketId}")
    public void createBucket(@PathVariable("bucketId") String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketService.createBucket(bucketId);
    }

    @DeleteMapping("/delete/{bucketId}")
    public void deleteBucket(@PathVariable("bucketId")String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketService.deleteBucket(bucketId);
    }

    @GetMapping("/exists/{bucketId}")
    public boolean doesBucketExists(@PathVariable("bucketId")String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return bucketService.doesBucketExists(bucketId);
    }
}
