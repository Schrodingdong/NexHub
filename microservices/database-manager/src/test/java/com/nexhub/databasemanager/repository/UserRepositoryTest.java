package com.nexhub.databasemanager.repository;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.neo4j.EmbeddedNeo4j;
import com.lordofthejars.nosqlunit.neo4j.Neo4jRule;
import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Disabled because of the dependency on the database
 * */
@DataNeo4jTest
@Disabled
class UserRepositoryTest {
    @Autowired
    private UserRepository testUserRepository;


    User user,user1,user2,user3,user4;
    List<User> allUsers;
    @BeforeEach
    void setUp() {
        user = new User("hamza", "hamzaalami@gmail.com","bucketid2");
        user1 = new User( "hamza", "hamzaalami1@gmail.com","bucketid2");
        user2 = new User( "hamza", "hamzaalami2@gmail.com","bucketid2");
        user3 = new User( "oumaima", "oumaimaAitBouchouar@gmail.com","bucketid2");
        user4 = new User( "ali", "aliNabloussi@gmail.com","bucketid2");
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
    void getUsersByName_nameExists() {
        String testName = "hamza";
        List<User> fetchedUserList;

        fetchedUserList = testUserRepository.getUsersByName(testName);
        List<User> expected = new ArrayList<>();
        expected.add(user);
        expected.add(user1);
        expected.add(user2);
        Assertions.assertThat(fetchedUserList)
                .filteredOn(el -> el.getUsername().equals(testName));

    }

    @Test
    void getUsersByName_nameDoesntExist(){
        String testNameInexistant = "adam";
        List<User> fetchedUserList;
        fetchedUserList = testUserRepository.getUsersByName(testNameInexistant);
        Assertions.assertThat(fetchedUserList)
                .isEmpty();
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
        List<User> fetchedList = testUserRepository.findAllUsers();
        User u1 = fetchedList.get(0);
        User u2 = fetchedList.get(1);
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

        List<User> fetchedList = testUserRepository.findAllUsers();
        User u1 = fetchedList.get(0);
        User u2 = fetchedList.get(1);
        User u3 = fetchedList.get(2);
        testUserRepository.followUser(u2.getUserId(), u1.getUserId());
        testUserRepository.followUser(u3.getUserId(), u1.getUserId());
        List<User> followers = testUserRepository.userFollowers(u1.getUserId());
        Assertions.assertThat(followers)
                .hasSize(2)
                .filteredOn(inUser -> inUser.getUserId() == u2.getUserId() || inUser.getUserId() == u1.getUserId());
    }
    @Test
    void userFollowing(){
        List<User> fetchedList = testUserRepository.findAllUsers();
        User u1 = fetchedList.get(0);
        User u2 = fetchedList.get(1);
        User u3 = fetchedList.get(2);

        testUserRepository.followUser(u1.getUserId(), u2.getUserId());
        testUserRepository.followUser(u1.getUserId(), u3.getUserId());
        List<User> following = testUserRepository.userFollowing(u1.getUserId());
        Assertions.assertThat(following)
                .hasSize(2)
                .filteredOn(inUser -> inUser.getUserId() == u2.getUserId() || inUser.getUserId() == u1.getUserId());
    }

}