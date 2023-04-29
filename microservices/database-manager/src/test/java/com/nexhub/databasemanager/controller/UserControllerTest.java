package com.nexhub.databasemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    private User user,user_noMail;
    @BeforeEach
    void setUp(){
        user = new User("schrodingdong","schrodingdong@gmail.com","bucketId");
        user_noMail = new User("schrodingdong", null,"bucketId");
    }

    @Test
    void httpRequestMatching_addUser() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpRequestMatching_getUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/get/id/69420")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpRequestMatching_getAllUsers() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/get/all")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpRequestMatching_deleteUser() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/delete/id/69420")
        ).andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void httpRequestMatching_getAllUsersOfName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/get/username/hamza")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void httpRequestMatching_updateUser() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/update/id/69420")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void noMail_addUser() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user_noMail))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void validInput_addUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        );

        ArgumentCaptor<User> captoe = ArgumentCaptor.forClass(User.class);
        BDDMockito.verify(userService).saveUser(captoe.capture());
        Assertions.assertThat(captoe.getValue().getUsername()).isEqualTo("schrodingdong");
        Assertions.assertThat(captoe.getValue().getMail()).isEqualTo("schrodingdong@gmail.com");
    }

    @Test
    void validInput_updateUser() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/update/id/69420")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getUserFollowing() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/get/69/following")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getUserFollowers() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/get/69/followers")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void followUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/69/follows/420")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}