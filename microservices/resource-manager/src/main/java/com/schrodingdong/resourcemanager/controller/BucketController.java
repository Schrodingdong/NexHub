package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.service.BucketService;
import io.minio.errors.*;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/buckets")
@CrossOrigin
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping("/create/{bucketId}")
    public ResponseEntity<?> createBucket(@PathVariable("bucketId") String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (bucketId == null || bucketId.equals(""))
            return ResponseEntity.badRequest().build();
        bucketService.createBucket(bucketId);
        return ResponseEntity.ok()
                .body(String.format("Bucket of id : '%s' created", bucketId));
    }

    @DeleteMapping("/delete/{bucketId}")
    public ResponseEntity<?> deleteBucket(@PathVariable("bucketId")String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (bucketId == null || bucketId.equals(""))
            return ResponseEntity.badRequest().build();
        bucketService.deleteBucket(bucketId);
        return ResponseEntity.ok()
                .body("Bucket of id : '"+bucketId+"' deleted");
    }

    @GetMapping("/exists/{bucketId}")
    public ResponseEntity<?> doesBucketExists(@PathVariable("bucketId")String bucketId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (bucketId == null || bucketId.equals(""))
            return ResponseEntity.badRequest().build();
        boolean exists = bucketService.doesBucketExists(bucketId);
        return ResponseEntity.ok()
                .body(String.format("Bucket of id : '%s' exists", bucketId));
    }
}
