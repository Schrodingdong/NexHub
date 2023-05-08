package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.model.FileUploadResponse;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.service.ResourceService;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/resource")
public class ResourceController {
    private final ResourceService resourceService;

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



    @GetMapping("/download")
    public ResponseEntity<?> downloadObject(@RequestParam String resourceId,
                                            @RequestParam String userMail,
                                            @RequestParam String downloadName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Resource resourceToDownload = resourceService.downloadResource(resourceId, userMail, downloadName);
        if(resourceToDownload == null)
            return new ResponseEntity<>("File Not Found :/", HttpStatus.NOT_FOUND);

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resourceToDownload.getFilename() + "\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resourceToDownload);
    }

    private static String getExtension(String fileName) {
        int extensionIndex = fileName.indexOf('.');
        String extension = fileName.substring(extensionIndex+1);
        return extension;
    }

    @DeleteMapping("/delete")
    public void deleteObject(@RequestParam String resourceId,
                             @RequestParam String userMail) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.deleteResource(resourceId, userMail);
    }

}