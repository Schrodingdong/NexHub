package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private AutoCloseable autoCloseable;
    private UserService testUserService;

    User user,user1,user2,user3,user4;
    List<User> allUsers;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        testUserService = new UserService(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getUsersByName() {
        String name = "hamza";
        testUserService.getUsersByName(name);
        Mockito.verify(userRepository).getUsersByName(name);
    }

    @Test
    void getUserById() {
        testUserService.getUserById(69L);
        Mockito.verify(userRepository).findById(69L);
    }

    @Test
    void getAllUsers() {
        testUserService.getAllUsers();
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void saveUser() throws Exception {
        User u = new User(1,"ha","ga");
        testUserService.saveUser(u);
        ArgumentCaptor<User> argumentCaptor =
                ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(argumentCaptor.capture());
        User capturedValue = argumentCaptor.getValue();
        Assertions.assertThat(capturedValue)
                .isEqualTo(u);

        BDDMockito.given(userRepository.isMailTaken(u.getMail()))
                        .willReturn(true);

        Assertions.assertThatThrownBy(() -> testUserService.saveUser(u))
                .hasMessageContaining("The email : " +u.getMail()+" is already taken :/");
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