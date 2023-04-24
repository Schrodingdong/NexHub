package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.model.DeleteObjectParams;
import com.schrodingdong.resourcemanager.model.DownloadObjectParams;
import com.schrodingdong.resourcemanager.model.FileUploadResponse;
import com.schrodingdong.resourcemanager.model.UploadObjectParams;
import com.schrodingdong.resourcemanager.service.ResourceService;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> uploadObject(@RequestParam String resId,
                                          @RequestParam String bucketName,
                                          @RequestParam ("file") MultipartFile multipartFile) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String fileName = multipartFile.getOriginalFilename();
        long size = multipartFile.getSize();
        String path = resourceService.uploadObject(multipartFile, fileName, resId, bucketName);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);

        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public void deleteObject(@RequestParam String objectName,
                             @RequestParam String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.deleteObject(objectName, bucketName);
    }

}