package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Disabled because of the dependency on the database
 * */
@ExtendWith(MockitoExtension.class)
@DataNeo4jTest
@Disabled
class ResourceRepositoryTest {
    @MockBean
    private UserRepository userRepository;
    @InjectMocks
    private ResourceRepository testResourceRepository;
    private Resource r1,r2,r3,r4;
    private User u1,u2;


    @BeforeEach
    void setUp(){
        u1 = new User("hamza", "hamzaalami@gmail.com","bucketId1");
        u2 = new User("oumaima", "oumaima2@gmail.com","bucketId2");
        u1 = userRepository.save(u1);
        u2 = userRepository.save(u2);
        Set<User> userSet1 = new HashSet<>();
        Set<User> userSet2 = new HashSet<>();
        userSet1.add(u1);
        userSet2.add(u2);

        System.out.println("[INFO] - instantiated : ");
        System.out.println(u1);
        System.out.println(u2);
        System.out.println("---------------------------------------------");
        r1 = new Resource(
                "guideToJava.pdf",
                "description1-u1",
                ResVisibility.PUBLIC.name()
        );
        r2 = new Resource(
                "Devops in a nutshell.pdf",
                "description2-u1",
                ResVisibility.PUBLIC.name()
        );
        r3 = new Resource(
                "SPRINGBOOT.pdf",
                "description1-private-u1",
                ResVisibility.PRIVATE.name()
        );
        r1.addHolder(u1);
        r2.addHolder(u1);
        r3.addHolder(u1);
        r4 = new Resource(
                "Devops in a nutshell.pdf",
                "description-u2",
                ResVisibility.PUBLIC.name()
        );
        r4.addHolder(u2);


        r1 = testResourceRepository.save(r1);
        r2 = testResourceRepository.save(r2);
        r3 = testResourceRepository.save(r3);
        r4 = testResourceRepository.save(r4);
        System.out.println("[INFO] - instantiated : ");
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
        System.out.println("---------------------------------------------");
    }

    @AfterEach
    void tearDown(){
        testResourceRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void getResourceByName_nameExists() {
        String name = "Devops in a nutshell.pdf";
        List<Resource> resourceList = testResourceRepository.getResourceByName(name);
        Assertions.assertThat(resourceList.size())
                .isEqualTo(2);
    }

    @Test
    void getResourceByName_nameDoesntExists() {
        String name = "NYAHAHAHAHA";
        List<Resource> resourceList = testResourceRepository.getResourceByName(name);
        Assertions.assertThat(resourceList.size())
                .isEqualTo(0);
    }

    @Test
    void getAllResourcesFromUser_userExists(){
        long userId = u1.getUserId();
        long resId1 = r1.getResourceId();
        long resId2 = r2.getResourceId();
        long resId3 = r3.getResourceId();
        // test method
        List<Resource> fetchedRes = testResourceRepository.getAllResourcesFromUser(userId);
        Assertions.assertThat(fetchedRes)
                .hasSize(3)
                .filteredOn(res -> res.getResourceId() == resId1 ||
                        res.getResourceId() == resId2 ||
                        res.getResourceId() == resId3);
    }

    @Test
    void getAllResourcesFromUser_userNotExists(){
        long userId = -1;
        // test method
        List<Resource> fetchedRes = testResourceRepository.getAllResourcesFromUser(userId);
        Assertions.assertThat(fetchedRes).isEmpty();
    }

    @Test
    void getAllPublicResourcesFromUser(){
        long userId = u1.getUserId();

        List<Resource> resPublicList = testResourceRepository.getAllPublicResourcesFromUser(userId);
        Assertions.assertThat(resPublicList)
                .hasSize(2)
                .filteredOn(res -> res.getResourceVisibility().equals("PUBLIC"));
    }

    @Test
    void linkToUser_userExists() {
        Resource rToAdd = new Resource("newToAdd.pdf","deschahah",ResVisibility.PUBLIC.name());
        rToAdd = testResourceRepository.save(rToAdd);
        long resId = rToAdd.getResourceId();
        long userId = u1.getUserId();
        testResourceRepository.linkToUser(
                resId,
                userId
        );
        Resource fetchedRes = testResourceRepository.findById(resId).get();
        Assertions.assertThat(
                    fetchedRes.getResourceHolders()
                )
                .filteredOn(user -> user.getUserId() == u1.getUserId());
    }

    @Test
    void deleteResourceById() {
        long resId = r4.getResourceId();
        testResourceRepository.deleteById(resId);
        Assertions.assertThat(testResourceRepository.findById(resId).isPresent())
                .isFalse();
    }
}