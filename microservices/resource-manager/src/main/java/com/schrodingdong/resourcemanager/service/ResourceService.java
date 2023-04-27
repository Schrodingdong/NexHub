package com.schrodingdong.resourcemanager.service;

import com.schrodingdong.resourcemanager.model.ResourceModel;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.model.UserModel;
import com.schrodingdong.resourcemanager.repository.ResourceRepository;
import com.schrodingdong.resourcemanager.util.FeignServiceResourceMetadataDbManager;
import com.schrodingdong.resourcemanager.util.FeignServiceUserMetadataDbManager;
import io.minio.errors.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

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
public class ResourceService implements IResourceService{
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private BucketService bucketService;
    @Autowired
    private FeignServiceUserMetadataDbManager userFeign;
    @Autowired
    private FeignServiceResourceMetadataDbManager resourceFeign;

    public ResourceService() {
    }

    @Override
    public void uploadResource(UploadResourceParams uploadParams) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.instanciateClient();
        Path tmpSavePath = getSavePath(uploadParams.getFileName());
        try (InputStream inputStream = uploadParams.getFileToUpload().getInputStream()) {
            Files.copy(inputStream, tmpSavePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + uploadParams.getFileName(), ioe);
        }
        // Save metadata
        ResourceModel resourceModel =
                saveResourceToMetadataDb(uploadParams.getFileName(), uploadParams.getResourceDescription(), uploadParams.getResourceVisibility(), uploadParams.getUserMail() );
        // Save to minio
        String bucketId= getBucketId(uploadParams.getUserMail());
        resourceRepository.uploadResource(
                tmpSavePath.toFile().getPath(),
                resourceModel.getResourceBucketId(),
                bucketId
        );
    }
    private Path getSavePath(String fileName){
        resourceRepository.instanciateClient();
        String extension = getExtension(fileName);
        String filePath = "fileToSave."+extension;
        return Paths.get(filePath);
    }

    @NotNull
    private static String getExtension(String fileName) {
        int extensionIndex = fileName.indexOf('.');
        String extension = fileName.substring(extensionIndex+1);
        return extension;
    }

    private ResourceModel saveResourceToMetadataDb(String resourceName, String resourceDescription, String resourceVisibility, String userMail){
        ResourceModel resourceMetadata = new ResourceModel(
                resourceName,
                resourceDescription.isEmpty()? "" : resourceDescription,
                resourceVisibility
        );
        // communicate with the other service
        // get userID by Mail
        UserModel fetchedUser = userFeign.getUserByMail(userMail);
        long userId = fetchedUser.getUserId();
        // save res and link to that userID
        return resourceFeign.addResourceToUser(userId, resourceMetadata);
    }
    private String getBucketId(String userMail){
        // get User object by mail
        UserModel fetchedUser = userFeign.getUserByMail(userMail);
        // get its bucketId
        return fetchedUser.getBucketId();
    }

//    @Deprecated
//    public String uploadResource(MultipartFile multipartFile, String fileName, String resBucketId, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        resourceRepository.instanciateClient();
//        // Save locally
//        int extensionIndex = fileName.indexOf('.');
//        String extension = fileName.substring(extensionIndex+1);
//        String filePath = "fileToSave."+extension;
//        Path uploadPath = Paths.get(filePath);
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException ioe) {
//            throw new IOException("Could not save file: " + fileName, ioe);
//        }
//        // save to db
//        resourceRepository.uploadResource(uploadPath.toFile().getPath(),resBucketId,bucketName);
//        return uploadPath.toFile().getPath();
//    }


    @Override
    public Resource downloadResource(String resourceId, String userMail, String downloadFileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.instanciateClient();
        // get the bucket name
        String bucketId = getBucketId(userMail);
        // download it to a temp folder
        String extension = getExtension(downloadFileName);
        String resPath = "fileToDownload."+extension;
        resourceRepository.downloadResource(
                resourceId,
                bucketId,
                resPath
        );
        // fetch it to send it in HTTP
        File f = new File(resPath);
        return (f != null)? new UrlResource(f.toURI()) : null;
    }

//    public Resource downloadObject(String objectName, String fileName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        resourceRepository.instanciateClient();
//        // download it to a temp folder
//        String extension = getExtension(fileName);
//        String resPath = "filetoDownload."+extension;
//        resourceRepository.downloadResource(objectName,resPath,bucketName);
//        // fetch it to send it in HTTP
//        File f = new File(resPath);
//        return (f != null)? new UrlResource(f.toURI()) : null;
//    }


    @Override
    public void deleteResource(String resourceId, String userMail) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.instanciateClient();
        // get the bucket name
        String bucketId = getBucketId(userMail);
        resourceRepository.deleteResource(
                resourceId,
                bucketId
        );

    }

//    public void deleteObject(String objectName, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        resourceRepository.instanciateClient();
//        resourceRepository.deleteResource(objectName, bucketName);
//    }
}
