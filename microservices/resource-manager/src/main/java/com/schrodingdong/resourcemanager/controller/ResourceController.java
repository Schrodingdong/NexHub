package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.model.FileUploadResponse;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.service.ResourceService;
import io.minio.errors.*;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadObject(@RequestParam ("file") MultipartFile multipartFile,
                                          @RequestParam String userMail,
                                          @RequestParam String resourceDescription,
                                          @RequestParam String resourceVisibility) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (multipartFile.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.NOT_FOUND);
        }
        UploadResourceParams resParams = new UploadResourceParams(multipartFile,userMail,resourceDescription,resourceVisibility);
        resourceService.uploadResource(resParams);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(resParams.getFileName());
        response.setSize(resParams.getSize());

        return ResponseEntity.ok()
                .body(response);
    }

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
    private String getExtension(String fileName) {
        int extensionIndex = fileName.indexOf('.');
        String extension = fileName.substring(extensionIndex+1);
        return extension;
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteObject(@RequestParam String resourceId,
                             @RequestParam String userMail) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceService.deleteResource(resourceId, userMail);
        return ResponseEntity.ok()
                .body("Resource of id : '"+resourceId+"' deleted");
    }

}