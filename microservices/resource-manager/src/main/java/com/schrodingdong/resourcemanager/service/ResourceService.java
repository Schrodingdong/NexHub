package com.schrodingdong.resourcemanager.service;

import ch.qos.logback.core.util.FileUtil;
import com.schrodingdong.resourcemanager.repository.ResourceRepository;
import io.minio.errors.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ResourceService {
    @Autowired
    private ResourceRepository resourceRepository;

    public String uploadObject(MultipartFile multipartFile, String fileName, String resBucketId, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Save locally
        int extensionIndex = fileName.indexOf('.');
        String extension = fileName.substring(extensionIndex+1);
        String filePath = "tmpRes/fileToSave."+extension;
        Path uploadPath = Paths.get(filePath);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        // save to db
        resourceRepository.uploadFile(uploadPath.toFile().getPath(),resBucketId,bucketName);
        return uploadPath.toFile().getPath();
    }

    public Resource downloadObject(String objectName, String fileName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
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
