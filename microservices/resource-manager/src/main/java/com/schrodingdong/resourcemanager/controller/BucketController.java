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

    @PostMapping("/create/{name}")
    public void createBucket(@PathVariable("name") String name) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketService.createBucket(name);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteBucket(@PathVariable("name")String name) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        bucketService.removeBucket(name);
    }

    @GetMapping("/exists/{name}")
    public boolean doesBucketExists(@PathVariable("name")String name) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return bucketService.doesBucketExists(name);
    }
}
