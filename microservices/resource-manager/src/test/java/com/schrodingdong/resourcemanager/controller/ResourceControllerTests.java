package com.schrodingdong.resourcemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schrodingdong.resourcemanager.model.UploadResourceParams;
import com.schrodingdong.resourcemanager.service.ResourceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// TODO : tests get ignored when building for some reason, but they all pass if we execute them standalone
@WebMvcTest(ResourceController.class)
public class ResourceControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResourceService mockResourceService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }
    @Test
    public void test0() throws Throwable {
        Assertions.assertThat(true).isTrue();
    }
    @Test
    void uploadObject() throws Exception {
        String fileExtension = "ext";
        // the name is important HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH im about to lose it h hh hhh hhhh hhhhh hhhhhh hhhhhhh
        MockMultipartFile multipartFile =  new MockMultipartFile("file", "test."+fileExtension, MediaType.TEXT_PLAIN_VALUE, "test".getBytes());
        String userMail = "test@gmail.com";
        String resourceDescription = "testDescription";
        String resourceVisibility = "PUBLIC";
        UploadResourceParams uploadParams = new UploadResourceParams(multipartFile, userMail, resourceDescription, resourceVisibility);

        mockMvc.perform(
                multipart("/resource/upload")
                        .file(multipartFile)
                        .param("userMail", userMail)
                        .param("resourceDescription", resourceDescription)
                        .param("resourceVisibility", resourceVisibility)
        ).andExpect(status().isOk());
        verify(mockResourceService).uploadResource(uploadParams);
    }

    @Test
    void downloadObject() throws Exception {
        String resourceId = "testResource";
        String userMail = "test@gmail.com";
        String downloadName = "fileIwaLOUUUUUUUUL";
        Resource res = mock(Resource.class);
        when(mockResourceService.downloadResource( resourceId, userMail, downloadName))
                .thenReturn(res);
        when(res.getFilename()).thenReturn("test.txt");

        mockMvc.perform(get("/resource/download")
                        .param("resourceId", resourceId)
                        .param("userMail", userMail)
                        .param("downloadName", downloadName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
        verify(mockResourceService)
                .downloadResource(resourceId, userMail, downloadName);
    }

    @Test
    void deleteObject() throws Exception {
        String resourceId = "testResource";
        String userMail = "test@gmail.com";
        mockMvc.perform(
                delete("/resource/delete", "testResource")
                        .param("resourceId", resourceId)
                        .param("userMail", userMail)
        ).andExpect(status().isOk());

        verify(mockResourceService)
                .deleteResource(resourceId, userMail);
    }
}
