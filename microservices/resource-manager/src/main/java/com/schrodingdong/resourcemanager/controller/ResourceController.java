package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.model.FileUploadResponse;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.service.ResourceService;
import io.minio.errors.*;
import org.jetbrains.annotations.NotNull;
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
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadObject(@RequestParam String userMail,
                                          @RequestParam String resourceDescription,
                                          @RequestParam String resourceVisibility,
                                          @RequestParam ("file") MultipartFile multipartFile) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        UploadResourceParams resParams = new UploadResourceParams(multipartFile,userMail,resourceDescription,resourceVisibility);
        resourceService.uploadResource(resParams);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(resParams.getFileName());
        response.setSize(resParams.getSize());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadObject(@RequestParam String resId,
//                                          @RequestParam String bucketName,
//                                          @RequestParam ("file") MultipartFile multipartFile) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        String fileName = multipartFile.getOriginalFilename();
//        long size = multipartFile.getSize();
//        String path = resourceService.uploadResource(multipartFile, fileName, resId, bucketName);
//
//        FileUploadResponse response = new FileUploadResponse();
//        response.setFileName(fileName);
//        response.setSize(size);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


    @GetMapping("/download")
    public ResponseEntity<?> downloadObject(@RequestParam String resourceId,
                                            @RequestParam String userMail,
                                            @RequestParam String downloadName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Resource resourceToDownload = resourceService.downloadResource(resourceId, userMail, downloadName);
        if(resourceToDownload == null)
            return new ResponseEntity<>("File Not Found :/", HttpStatus.NOT_FOUND);

        String extension = getExtension(resourceToDownload.getFilename());
        String filename = downloadName+"."+extension;
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + filename + "\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resourceToDownload);
    }

    @NotNull
    private static String getExtension(String fileName) {
        int extensionIndex = fileName.indexOf('.');
        String extension = fileName.substring(extensionIndex+1);
        return extension;
    }

//    @GetMapping("/download")
//    public ResponseEntity<?> downloadObject(@RequestParam String objectName,
//                                            @RequestParam String fileName,
//                                            @RequestParam String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        // get file extensions
//        Resource res = resourceService.downloadObject(objectName, fileName, bucketName);
//        String fetchedFileName = res.getFile().getName();
//        int extensionIndex = fetchedFileName.indexOf('.');
//        String extension = fileName.substring(extensionIndex+1);
//        fileName = fileName + "." + extension;
//        String contentType = "application/octet-stream";
//        String headerValue = "attachment; filename=\"" + fileName + "\"";
//        if(res == null)
//            return new ResponseEntity<>("File Not Found :/", HttpStatus.NOT_FOUND);
//
//        return ResponseEntity.ok()
//                        .contentType(MediaType.parseMediaType(contentType))
//                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
//                        .body(res);
//
//    }

    @DeleteMapping("/delete")
    public void deleteObject(@RequestParam String resourceId,
                             @RequestParam String userMail) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.deleteResource(resourceId, userMail);
    }

}