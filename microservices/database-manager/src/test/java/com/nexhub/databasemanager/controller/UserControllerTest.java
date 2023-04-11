package com.nexhub.databasemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

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

    private User user;
    @BeforeEach
    void setUp(){
        user = new User(69420,"schrodingdong","schrodingdong@gmail.com");
    }


    @Test
    void addUser() {
    }

    @Test
    void httpRequestMatching_getUser() throws Exception {
    }

    @Test
    void getAllUsers() throws Exception {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getAllUsersOfName() throws Exception {
    }

    @Test
    void updateUser() {
    }
}