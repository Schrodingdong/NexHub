package com.schrodingdong.resourcemanager.service;

import ch.qos.logback.core.util.FileUtil;
import com.schrodingdong.resourcemanager.repository.ResourceRepository;
import io.minio.errors.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public void uploadObject(String fileName, String resBucketId, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.uploadFile(fileName,resBucketId,bucketName);
    }

    public Resource downloadObject(String objectName, String fileName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // delete tmp folder content
        FileUtils.cleanDirectory(new File("tmpRes"));
        // download it to a temp folder
        int extensionIndex = fileName.indexOf('.');
        String resPath = "tmpRes/filetoDownload"+fileName.substring(extensionIndex);
        resourceRepository.downloadObject(objectName,resPath,bucketName);
        // fetch it to send it in HTTP
        File f = new File(resPath);
        return (f != null)? new UrlResource(f.toURI()) : null;
    }

    public void deleteObject(String objectName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.deleteObject(objectName, bucketName);
    }
}
