package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.model.DeleteObjectParams;
import com.schrodingdong.resourcemanager.model.DownloadObjectParams;
import com.schrodingdong.resourcemanager.model.UploadObjectParams;
import com.schrodingdong.resourcemanager.service.ResourceService;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
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
    @GetMapping(
            value = "/download"
    )
    public ResponseEntity<?> downloadObject(@RequestParam String objectName,
                                         @RequestParam String fileName,
                                         @RequestParam String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + fileName + "\"";
        Resource res = resourceService.downloadObject(objectName, fileName, bucketName);

        if(res == null)
            return new ResponseEntity<>("File Not Found :/", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                        .body(res);

    }

    @DeleteMapping("/delete")
    public void deleteObject(@RequestParam DeleteObjectParams params) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.deleteObject(params.getObjectName(), params.getBucketName());
    }

}