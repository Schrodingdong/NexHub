package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private AutoCloseable autoCloseable;
    private UserService testUserService;

    private User user_old,user,modifiedUser_sameId_diffUsername,modifiedUser_diffId,modifiedUser_sameId_diffMail;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testUserService = new UserService(userRepository);

        user_old = new User(69,"schrodingdong","schrodingdong@gmail.com"); // added because passing by reference problems
        user = new User(69,"schrodingdong","schrodingdong@gmail.com");
        modifiedUser_sameId_diffUsername = new User(69,"newName","schrodingdong@gmail.com");
        modifiedUser_sameId_diffMail = new User(69,"schrodingdong","othermail@gmail.com");
        modifiedUser_diffId = new User(420,"newName","schrodingdong@gmail.com");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getUsersByName_nameGiven() {
        String name = "hamza";
        testUserService.getUsersByName(name);
        Mockito.verify(userRepository).getUsersByName(name);
    }

    @Test
    void getUsersByName_nameNull() {
        String name = null;
        List<User> fetched = testUserService.getUsersByName(name);
        Mockito.verify(userRepository).getUsersByName(name);
        Assertions.assertThat(fetched).isEmpty();
    }

    @Test
    void getUserById_idGiven() {
        testUserService.getUserById(69L);
        Mockito.verify(userRepository).findById(69L);
    }

    @Test
    void getUserById_idNull() {
        User u = testUserService.getUserById(null);
        Mockito.verify(userRepository).findById(null);
        Assertions.assertThat(u).isNull();
    }

    @Test
    void getAllUsers() {
        testUserService.getAllUsers();
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void saveUser_mailDiff() throws Exception {
        testUserService.saveUser(user);
        ArgumentCaptor<User> argumentCaptor =
                ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(argumentCaptor.capture());
        User capturedValue = argumentCaptor.getValue();
        Assertions.assertThat(capturedValue)
                .isEqualTo(user);
    }

    @Test
    void savedUser_mailTaken(){
        BDDMockito.given(userRepository.isMailTaken(user.getMail()))
                .willReturn(true);
        Assertions.assertThatThrownBy(() -> testUserService.saveUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The email : " +user.getMail()+" is already taken :/");
    }

    @Test
    void updateUser_sameUser_diffUsername(){
        long sameId = 69;
        BDDMockito.given(userRepository.findById(sameId))
                .willReturn(Optional.of(user));
        User returnedUser = testUserService.updateUser(sameId, modifiedUser_sameId_diffUsername);
        Assertions.assertThat(returnedUser.getUsername()).isNotEqualTo(user_old.getUsername());
        Assertions.assertThat(returnedUser.getMail()).isEqualTo(user_old.getMail());
        Assertions.assertThat(returnedUser.getUserId()).isEqualTo(user_old.getUserId());
    }

    @Test
    void updateUser_sameUser_diffMail(){
        long sameId = 69;
        BDDMockito.given(userRepository.findById(sameId))
                .willReturn(Optional.of(user));
        System.out.println(modifiedUser_sameId_diffMail);
        User returnedUser = testUserService.updateUser(sameId, modifiedUser_sameId_diffMail);
        Assertions.assertThat(returnedUser.getUsername()).isEqualTo(user_old.getUsername());
        Assertions.assertThat(returnedUser.getMail()).isNotEqualTo(user_old.getMail());
        Assertions.assertThat(returnedUser.getUserId()).isEqualTo(user_old.getUserId());
    }

    @Test
    void updateUser_differentUser(){
        long id2 = 420;
        BDDMockito.given(userRepository.findById(id2))
                .willReturn(Optional.empty());
        User returnedUser = testUserService.updateUser(id2, modifiedUser_diffId);
        Assertions.assertThat(returnedUser.getUserId()).isNotEqualTo(user_old.getUserId());
    }

    @Test
    void updateUser_noData(){
        Assertions.assertThatThrownBy(() -> testUserService.updateUser(69, null))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void saveUser_nullUser(){
        Assertions.assertThatThrownBy(() -> testUserService.saveUser(null))
                .isInstanceOf(NullPointerException.class);
    }
    @Test
    void deleteUser() {
        testUserService.deleteUser(1);
        Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteAll() {
        testUserService.deleteAll();
        Mockito.verify(userRepository).deleteAll();
    }
}