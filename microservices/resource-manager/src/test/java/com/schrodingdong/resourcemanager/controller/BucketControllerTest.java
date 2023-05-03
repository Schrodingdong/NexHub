package com.schrodingdong.resourcemanager.controller;

import com.schrodingdong.resourcemanager.service.BucketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BucketController.class)
class BucketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BucketService mockBucketService;
    private String bucketName = "bucketname";

    @Test
    void createBucket() throws Exception {
        mockMvc.perform(
                post("/buckets/create/{bucketId}", bucketName)
        ).andExpect(status().isOk());
        Mockito.verify(mockBucketService).createBucket(bucketName);
    }

    @Test
    void deleteBucket() throws Exception {
        mockMvc.perform(
                delete("/buckets/delete/{bucketId}", bucketName)
        ).andExpect(status().isOk());
        Mockito.verify(mockBucketService).deleteBucket(bucketName);
    }

    @Test
    void doesBucketExists() throws Exception {
        Mockito.when(mockBucketService.doesBucketExists(bucketName)).thenReturn(true);
        mockMvc.perform(
                get("/buckets/exists/{bucketId}", bucketName)
        ).andExpect(status().isOk());
        Mockito.verify(mockBucketService).doesBucketExists(bucketName);
    }
}