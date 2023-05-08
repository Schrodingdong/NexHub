package com.schrodingdong.resourcemanager.service;

import com.schrodingdong.resourcemanager.model.ResourceModel;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.model.UserModel;
import com.schrodingdong.resourcemanager.repository.ResourceRepository;
import com.schrodingdong.resourcemanager.util.FeignServiceResourceMetadataDbManagerResourceManagerService;
import com.schrodingdong.resourcemanager.util.FeignServiceUserMetadataDbManagerResourceManagerService;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ResourceService implements IResourceService{

    private final ResourceRepository resourceRepository;
    private final BucketService bucketService;
    private final FeignServiceUserMetadataDbManagerResourceManagerService userFeign;
    private final FeignServiceResourceMetadataDbManagerResourceManagerService resourceFeign;


    @Override
    public void uploadResource(UploadResourceParams uploadParams) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.instanciateClient();
        Path tmpSavePath = getSavePath(uploadParams.getFileName());
        try (InputStream inputStream = uploadParams.getFileToUpload().getInputStream()) {
            Files.copy(inputStream, tmpSavePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + uploadParams.getFileName(), ioe);
        }
        // get User li m3ni bl2amr object by mail
        UserModel fetchedUser = userFeign.getUserByMail(uploadParams.getUserMail());
        // Save metadata
        ResourceModel resourceModel =
                saveResourceToMetadataDb(
                        uploadParams.getFileName(),
                        uploadParams.getResourceDescription(),
                        uploadParams.getResourceVisibility(),
                        fetchedUser.getUserId());
        // Save to minio
        String bucketId = fetchedUser.getBucketId();
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

    private static String getExtension(String fileName) {
        int extensionIndex = fileName.indexOf('.');
        String extension = fileName.substring(extensionIndex+1);
        return extension;
    }

    private ResourceModel saveResourceToMetadataDb(String resourceName, String resourceDescription, String resourceVisibility, long userId){
        ResourceModel resourceMetadata = new ResourceModel(
                resourceName,
                resourceDescription == null ? "" : resourceDescription,
                resourceVisibility
        );
        // communicate with the other service
        System.out.println("my resource : " + resourceMetadata);
        System.out.println("to user : " + userId);
        return resourceFeign.addResourceToUser(userId, resourceMetadata);
    }

    @Override
    public Resource downloadResource(String resourceId, String userMail, String downloadFileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.instanciateClient();
        // get the bucket name
        UserModel fetchedUser = userFeign.getUserByMail(userMail);
        System.out.println("fetched user : " + fetchedUser);
        String bucketId = fetchedUser.getBucketId();
        // download it to a temp folder
        String extension = getExtension(downloadFileName);
        String resPath = "fileToDownload."+extension;
        resourceRepository.downloadResource(
                resourceId,
                resPath,
                bucketId
        );
        // fetch it to send it in HTTP
        File f = new File(resPath);
        return (f != null)? new UrlResource(f.toURI()) : null;
    }

    @Override
    public void deleteResource(String resourceId, String userMail) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        resourceRepository.instanciateClient();
        // get the bucket name
        UserModel fetchedUser = userFeign.getUserByMail(userMail);
        String bucketId = fetchedUser.getBucketId();
        resourceRepository.deleteResource(
                resourceId,
                bucketId
        );

    }
}
