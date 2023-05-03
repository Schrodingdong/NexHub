package com.schrodingdong.resourcemanager.service;

import com.schrodingdong.resourcemanager.model.ResourceModel;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.model.UserModel;
import com.schrodingdong.resourcemanager.repository.ResourceRepository;
import com.schrodingdong.resourcemanager.util.FeignServiceResourceMetadataDbManagerResourceManagerService;
import com.schrodingdong.resourcemanager.util.FeignServiceUserMetadataDbManagerResourceManagerService;
import io.minio.errors.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class ResourceServiceTest {

    private ResourceService resourceServiceToTest;
    private ResourceRepository resourceRepository;
    private BucketService bucketService;
    private FeignServiceResourceMetadataDbManagerResourceManagerService feignResMeta;
    private FeignServiceUserMetadataDbManagerResourceManagerService feignUserMet;
    @BeforeEach
    void setupService(){
        resourceRepository = Mockito.mock(ResourceRepository.class);
        bucketService = Mockito.mock(BucketService.class);
        feignResMeta = Mockito.mock(FeignServiceResourceMetadataDbManagerResourceManagerService.class);
        feignUserMet = Mockito.mock(FeignServiceUserMetadataDbManagerResourceManagerService.class);
        resourceServiceToTest = new ResourceService(
                resourceRepository,
                bucketService,
                feignUserMet,
                feignResMeta
        );
    }

    @Test
    void uploadResource() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // given
        String fileExtension = "ext";
        UploadResourceParams uploadParams = new UploadResourceParams(
                new MockMultipartFile("test", "test."+fileExtension, MediaType.TEXT_PLAIN_VALUE, "test".getBytes()),
                "test",
                "test",
                "test"
        );
        UserModel userModel = new UserModel(
                42069L,
                "test",
                "test@gmail.com"
        );
        ResourceModel resourceModel = new ResourceModel(
                uploadParams.getFileName(),
                uploadParams.getResourceDescription().isEmpty()? "" : uploadParams.getResourceDescription(),
                uploadParams.getResourceVisibility()
        );
        Mockito.when(feignUserMet.getUserByMail(uploadParams.getUserMail()))
                .thenReturn(userModel);

        // when
        resourceServiceToTest.uploadResource(uploadParams);

        // then
        ArgumentCaptor<ResourceModel> resourceModelArgumentCaptor = ArgumentCaptor.forClass(ResourceModel.class);
        Mockito.verify(feignResMeta)
                .addResourceToUser(Mockito.eq(userModel.getUserId()), resourceModelArgumentCaptor.capture());
        Mockito.verify(resourceRepository)
                .uploadResource(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void downloadResource() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String userMail = "test@gmail.com";
        UserModel userModel = new UserModel(
                42069L,
                "test",
                userMail
        );
        Mockito.when(feignUserMet.getUserByMail(userMail))
                .thenReturn(userModel);
        Resource res = resourceServiceToTest.downloadResource("test",userMail, "test.ext");

        Mockito.verify(feignUserMet).getUserByMail(Mockito.any());
        Mockito.verify(resourceRepository).downloadResource(Mockito.eq("test"),Mockito.any(),Mockito.eq("fileToDownload.ext"));
        Assertions.assertThat(res)
                .isNotNull();
        Assertions.assertThat(res.getFilename())
                .isEqualTo("fileToDownload.ext");
    }

    @Test
    void deleteResource() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String userMail = "test@gmail.com";
        UserModel userModel = new UserModel(
                42069L,
                "test",
                userMail
        );
        Mockito.when(feignUserMet.getUserByMail(userMail))
                .thenReturn(userModel);
        resourceServiceToTest.deleteResource("test", userMail);
        Mockito.verify(resourceRepository).deleteResource(Mockito.eq("test"), Mockito.any());
    }

}