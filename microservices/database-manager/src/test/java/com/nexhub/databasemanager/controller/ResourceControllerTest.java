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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ResourceController.class)
class ResourceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ResourceService resourceService;
    private ObjectMapper objectMapper;

    private Resource resource;
    private User user;
    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
        resource = new Resource(
                "guideToJava.pdf",
                "description1-u1",
                ResVisibility.PUBLIC.name()
        );
        user = new User("schrodingdong","schrodingdong@gmail.com","bucketId");
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