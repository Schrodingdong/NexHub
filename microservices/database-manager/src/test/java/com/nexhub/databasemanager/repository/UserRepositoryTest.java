package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository testUserRepository;

    User user,user1,user2,user3,user4;
    List<User> allUsers;
    @BeforeEach
    void setUp() {
        user = new User(0, "hamza", "hamzaalami@gmail.com");
        user1 = new User(1, "hamza", "hamzaalami1@gmail.com");
        user2 = new User(2, "hamza", "hamzaalami2@gmail.com");
        user3 = new User(3, "oumaima", "oumaimaAitBouchouar@gmail.com");
        user4 = new User(4, "ali", "aliNabloussi@gmail.com");
        allUsers = new ArrayList<>();
        allUsers.add(user);
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        System.out.println("[INFO] - Instantiated the following users :");
        for(User u : allUsers){
            System.out.println(u.toString());
        }

        testUserRepository.save(user);
        testUserRepository.save(user1);
        testUserRepository.save(user2);
        testUserRepository.save(user3);
        testUserRepository.save(user4);
        System.out.println("[INFO] - Users saved successfully");

    }

    @AfterEach
    void tearDown() {
        testUserRepository.deleteAll();
    }

    @Test
    void getUsersByName() {
        // testcase : name exists
        String testName = "hamza";
        List<User> fetchedUserList = testUserRepository.getUsersByName(testName);

        List<User> expected = new ArrayList<>();
        expected.add(user);
        expected.add(user1);
        expected.add(user2);
        Assertions.assertThat(fetchedUserList)
                .filteredOn(el -> el.getUsername().equals(testName));

        // testcase : name doesnt exist
        String testNameInexistant = "adam";
        fetchedUserList = testUserRepository.getUsersByName(testNameInexistant);
        Assertions.assertThat(fetchedUserList)
                .isEqualTo(new ArrayList<>());
    }

    @Test
    void isMailTaken() {
        // is taken
        String mail = "hamzaalami@gmail.com";
        boolean mailTaken = testUserRepository.isMailTaken(mail);
        Assertions.assertThat(mailTaken).isTrue();

        // not taken :
        String mail2 = "myCustomMail@gmail.com";
        boolean mailTaken2 = testUserRepository.isMailTaken(mail2);
        Assertions.assertThat(mailTaken2).isFalse();
    }


    @Test
    void getUserByUd_notExists(){
        long id = 69;
        Optional<User> u = testUserRepository.findById(id);
        Assertions.assertThat(u.isPresent())
                .isFalse();
    }
}