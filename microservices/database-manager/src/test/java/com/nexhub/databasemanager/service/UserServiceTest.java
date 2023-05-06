package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

        user_old = new User("schrodingdong","schrodingdong@gmail.com","bucketId2"); // added because passing by reference problems
        user = new User("schrodingdong","schrodingdong@gmail.com","bucketId2");
        modifiedUser_sameId_diffUsername = new User("newName","schrodingdong@gmail.com","bucketId2");
        modifiedUser_sameId_diffMail = new User("schrodingdong","othermail@gmail.com","bucketId2");
        modifiedUser_diffId = new User("newName","schrodingdong@gmail.com","bucketId2");
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
        Mockito.verify(userRepository).findByUserId(69L);
    }

    @Test
    void getUserById_idNull() {
        User u = testUserService.getUserById(-1L);
        Mockito.verify(userRepository).findByUserId(-1L);
        Assertions.assertThat(u).isNull();
    }

    @Test
    void getAllUsers() {
        testUserService.getAllUsers();
        Mockito.verify(userRepository).findAllUsers();
    }

    @Test
    void getUserFollowers(){
        testUserService.getUserFollowers(69L);
        Mockito.verify(userRepository).userFollowers(69L);
    }

    @Test
    void getUserFollowing(){
        testUserService.getUserFollowing(69L);
        Mockito.verify(userRepository).userFollowing(69L);
    }

    @Test
    void saveUser_mailDiff() throws Exception {
        testUserService.saveUser(user);
        ArgumentCaptor<String> captoe1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captoe2 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> captoe3 = ArgumentCaptor.forClass(String.class);
        Mockito.verify(userRepository).saveToGraph(captoe1.capture(), captoe2.capture(), captoe3.capture());
        Assertions.assertThat(captoe1.getValue())
                .isEqualTo(user.getUsername());
        Assertions.assertThat(captoe2.getValue())
                .isEqualTo(user.getMail());
        Assertions.assertThat(captoe3.getValue())
                .isEqualTo(user.getBucketId());
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
    void followUser(){
        testUserService.followUser(1L,2L);
        Mockito.verify(userRepository).followUser(1l,2l);
    }

    @Test
    @Disabled
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
    @Disabled
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
    @Disabled
    void updateUser_differentUser(){
        long id2 = 420;
        BDDMockito.given(userRepository.findById(id2))
                .willReturn(Optional.empty());
        ArgumentCaptor<User> captoe = ArgumentCaptor.forClass(User.class);
        User returnedUser = testUserService.updateUser(id2, modifiedUser_diffId);
        Mockito.verify(userRepository).save(captoe.capture());
        User caughtUser = captoe.getValue();
        Assertions.assertThat(caughtUser).isEqualTo(modifiedUser_diffId);
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