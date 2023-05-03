package com.schrodingdong.resourcemanager.service;

import com.schrodingdong.resourcemanager.model.ResourceModel;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.model.UserModel;
import com.schrodingdong.resourcemanager.repository.ResourceRepository;
import com.schrodingdong.resourcemanager.util.FeignServiceResourceMetadataDbManagerResourceManagerService;
import com.schrodingdong.resourcemanager.util.FeignServiceUserMetadataDbManagerResourceManagerService;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final ResourceRepository resourceRepository;
    private final BucketService bucketService;
    private final FeignServiceUserMetadataDbManagerResourceManagerService userFeign;
    private final FeignServiceResourceMetadataDbManagerResourceManagerService resourceFeign;

    public ResourceService(ResourceRepository resourceRepository, BucketService bucketService, FeignServiceUserMetadataDbManagerResourceManagerService userFeign, FeignServiceResourceMetadataDbManagerResourceManagerService resourceFeign) {
        this.resourceRepository = resourceRepository;
        this.bucketService = bucketService;
        this.userFeign = userFeign;
        this.resourceFeign = resourceFeign;
    }

    @Override
    public void uploadResource(UploadResourceParams uploadParams) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // get tmp save path
        Path tmpSavePath = getTmpSavePath(uploadParams);

        // get user and resource models
        UserModel userModel = userFeign.getUserByMail(uploadParams.getUserMail());
        ResourceModel resourceModel = new ResourceModel(
                uploadParams.getFileName(),
                uploadParams.getResourceDescription().isEmpty()? "" : uploadParams.getResourceDescription(),
                uploadParams.getResourceVisibility()
        );
        
        // save to Db
        saveToMetadataDb(uploadParams, userModel, resourceModel);
        saveToMinio(tmpSavePath, userModel, resourceModel);
    }

    private Path getTmpSavePath(UploadResourceParams uploadParams) throws IOException {
        // get path to save the tmp file
        Path tmpSavePath;
        String extension = getExtension(uploadParams.getFileName());
        String filePath = "fileToSave."+extension;
        tmpSavePath = Paths.get(filePath);
        // Save to the tmp file
        try (InputStream inputStream = uploadParams.getFileToUpload().getInputStream()) {
            Files.copy(inputStream, tmpSavePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + uploadParams.getFileName(), ioe);
        }
        return tmpSavePath;
    }

    private void saveToMetadataDb(UploadResourceParams uploadParams, @Valid UserModel userModel,@Valid ResourceModel resourceModel) {
        long userId = userModel.getUserId();
        resourceFeign.addResourceToUser(userId, resourceModel);
    }

    private void saveToMinio(Path tmpSavePath, @Valid UserModel userModel, @Valid ResourceModel resourceModel) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketId = userModel.getBucketId();
        resourceRepository.uploadResource(
                tmpSavePath.toFile().getPath(),
                resourceModel.getResourceBucketId(),
                bucketId
        );
    }

    private String getExtension(String fileName) {
        int extensionIndex = fileName.indexOf('.');
        return fileName.substring(extensionIndex+1);
    }

    @Override
    public Resource downloadResource(String resourceId, String userMail, String downloadFileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // get the bucket name
        UserModel userModel = userFeign.getUserByMail(userMail);
        String bucketId = userModel.getBucketId();
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
        return new UrlResource(f.toURI());
    }


    @Override
    public void deleteResource(String resourceId, String userMail) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // get the bucket name
        UserModel userModel = userFeign.getUserByMail(userMail);
        String bucketId = userModel.getBucketId();
        resourceRepository.deleteResource(
                resourceId,
                bucketId
        );

    }

}
