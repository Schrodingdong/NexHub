package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.model.DeleteObjectParams;
import com.schrodingdong.resourcemanager.model.DownloadObjectParams;
import com.schrodingdong.resourcemanager.model.UploadObjectParams;
import com.schrodingdong.resourcemanager.service.ResourceService;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/resource")
@CrossOrigin
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    public void uploadObject(@RequestBody UploadObjectParams params) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.uploadObject(params.getFileName(), params.getResId(), params.getBucketName());
    }
    @GetMapping("/download")
    public void downloadObject(@RequestParam DownloadObjectParams params) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.downloadObject(params.getObjectName(), params.getFileName(), params.getBucketName());
    }

    @DeleteMapping("/delete")
    public void deleteObject(@RequestParam DeleteObjectParams params) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.deleteObject(params.getObjectName(), params.getBucketName());
    }

}