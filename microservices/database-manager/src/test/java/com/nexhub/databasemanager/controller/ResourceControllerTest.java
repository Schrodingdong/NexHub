package com.nexhub.databasemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.ResourceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ResourceControllerTest {
    @Mock
    private ResourceService resourceService;
    private ResourceController resourceController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private AutoCloseable autoCloseable;

    private Resource resource;
    private User user;
    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        resourceController = new ResourceController(resourceService);
        resource = new Resource(
                "guideToJava",
                "description1-u1",
                ResVisibility.PUBLIC.name()
        );
        user = new User("schrodingdong","schrodingdong@gmail.com");
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void addResourceForUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/res/add/69420")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getResource() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/res/get/153")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllResources() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/res/get/all")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllResourcesOfName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/res/get/from-res-name/someName")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getALlResourcesFromUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/res/get/all-from-user/69")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllPublicResourcesFromUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/res/get/public-from-user/60")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Disabled
    void updateResourceForUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/res/update/658")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteResourceFromUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/res/delete/8394")
        ).andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void deleteAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/res/delete/all")
        ).andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}