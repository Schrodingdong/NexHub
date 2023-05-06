package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository testUserRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    User user,user1,user2,user3,user4;
    List<User> allUsers;
    @BeforeEach
    void setUp() {
        user = new User("hamza", "hamzaalami@gmail.com","bucketId2");
        user1 = new User( "hamza", "hamzaalami1@gmail.com","bucketId2");
        user2 = new User( "hamza", "hamzaalami2@gmail.com","bucketId2");
        user3 = new User( "oumaima", "oumaimaAitBouchouar@gmail.com","bucketId2");
        user4 = new User( "ali", "aliNabloussi@gmail.com","bucketId2");
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
        resourceRepository.deleteAll();
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

    @Test
    void followUser(){
        User u1 = testUserRepository.findAll().get(0);
        User u2 = testUserRepository.findAll().get(1);
        testUserRepository.followUser(u1.getUserId(), u2.getUserId());
        Set<User> u1Following = testUserRepository.findById(u1.getUserId()).get().getUserFollowing();
        Set<User> u2Followers = testUserRepository.findById(u2.getUserId()).get().getUserFollowers();
        Assertions.assertThat(u1Following)
                .filteredOn(inUser -> inUser.getUserId() == u2.getUserId());
        Assertions.assertThat(u2Followers)
                .filteredOn(inUser -> inUser.getUserId() == u1.getUserId());
    }

    @Test
    void userFollowers(){
        User u1 = testUserRepository.findAll().get(0);
        User u2 = testUserRepository.findAll().get(1);
        User u3 = testUserRepository.findAll().get(2);
        testUserRepository.followUser(u2.getUserId(), u1.getUserId());
        testUserRepository.followUser(u3.getUserId(), u1.getUserId());
        List<User> followers = testUserRepository.userFollowers(u1.getUserId());
        Assertions.assertThat(followers)
                .hasSize(2)
                .filteredOn(inUser -> inUser.getUserId() == u2.getUserId() || inUser.getUserId() == u1.getUserId());
    }
    @Test
    void userFollowing(){
        User u1 = testUserRepository.findAll().get(0);
        User u2 = testUserRepository.findAll().get(1);
        User u3 = testUserRepository.findAll().get(2);
        testUserRepository.followUser(u1.getUserId(), u2.getUserId());
        testUserRepository.followUser(u1.getUserId(), u3.getUserId());
        List<User> following = testUserRepository.userFollowing(u1.getUserId());
        Assertions.assertThat(following)
                .hasSize(2)
                .filteredOn(inUser -> inUser.getUserId() == u2.getUserId() || inUser.getUserId() == u1.getUserId());
    }

}