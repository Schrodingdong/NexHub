package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ResourceRepositoryTest {
    @Autowired
    private ResourceRepository testResourceRepository;
    @Autowired
    private UserRepository userRepository;

    private Resource r1,r2,r3,r4;
    private User u1,u2;


    @BeforeEach
    void setUp(){
        u1 = new User(0, "hamza", "hamzaalami@gmail.com");
        u2 = new User(1, "oumaima", "oumaima2@gmail.com");
        userRepository.save(u1);
        userRepository.save(u2);
        u1 = userRepository.findAll().get(0);
        u2 = userRepository.findAll().get(1);
        Set<User> userSet1 = new HashSet<>();
        Set<User> userSet2 = new HashSet<>();
        userSet1.add(u1);
        userSet2.add(u2);

        System.out.println("[INFO] - instantiated : ");
        System.out.println(u1);
        System.out.println(u2);
        r1 = new Resource(
                0,
                "guideToJava",
                "description1-u1",
                "kal",
                ResVisibility.PUBLIC.name()
        );
        r2 = new Resource(
                1,
                "Devops in a nutshell",
                "description2-u1",
                "hhh",
                ResVisibility.PUBLIC.name()
        );
        r3 = new Resource(
                2,
                "SPRINGBOOT",
                "description1-private-u1",
                "lmao",
                ResVisibility.PRIVATE.name()
        );
        r1.addHolder(u1);
        r2.addHolder(u1);
        r3.addHolder(u1);
        r4 = new Resource(
                3,
                "Devops in a nutshell",
                "description-u2",
                "heh",
                ResVisibility.PUBLIC.name()
        );
        r4.addHolder(u2);

        System.out.println("[INFO] - instantiated : ");
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
        Resource tmp = testResourceRepository.save(r1);
        System.out.println(tmp);
        testResourceRepository.save(r2);
        testResourceRepository.save(r3);
        testResourceRepository.save(r4);
        System.out.println(testResourceRepository.findAll());
    }

    @AfterEach
    void tearDown(){
        testResourceRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void getResourceByName_nameExists() {
        String name = "Devops in a nutshell";
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
        List<User> userList= userRepository.findAll();
        long userId = userList.get(0).getUserId();
        List<Resource> resList = testResourceRepository.findAll();
        long resId1 = resList.get(0).getResourceId();
        long resId2 = resList.get(1).getResourceId();
        // link resources to it
        testResourceRepository.linkToUser(resId1,userId);
        testResourceRepository.linkToUser(resId2,userId);
        // test method
        System.out.println(userList.get(0));
        List<Resource> fetchedRes = testResourceRepository.getAllResourcesFromUser(userId);
        Assertions.assertThat(fetchedRes)
                .hasSize(2)
                .filteredOn(res -> res.getResourceId() == resId1 || res.getResourceId() == resId2);
    }

    @Test
    void getAllResourcesFromUser_userNotExists(){
        long userId = -1;
        List<Resource> resList = testResourceRepository.findAll();
        long resId1 = resList.get(0).getResourceId();
        long resId2 = resList.get(1).getResourceId();
        // link resources to it
        testResourceRepository.linkToUser(resId1,userId);
        testResourceRepository.linkToUser(resId2,userId);
        // test method
        List<Resource> fetchedRes = testResourceRepository.getAllResourcesFromUser(userId);
        Assertions.assertThat(fetchedRes).isEmpty();
    }

    @Test
    void getAllPublicResourcesFromUser(){
        List<User> userList= userRepository.findAll();
        long userId = userList.get(0).getUserId();
        List<Resource> resList = testResourceRepository.findAll();
        long resId1 = resList.get(0).getResourceId();
        long resId2 = resList.get(1).getResourceId();
        long resId3 = resList.get(2).getResourceId();
        long resId4 = resList.get(3).getResourceId();
        // link resources to it
        testResourceRepository.linkToUser(resId1,userId);
        testResourceRepository.linkToUser(resId2,userId);
        testResourceRepository.linkToUser(resId3,userId);
        testResourceRepository.linkToUser(resId4,userId);

        List<Resource> resPublicList = testResourceRepository.getAllPublicResourcesFromUser(userId);
        Assertions.assertThat(resPublicList)
                .filteredOn(res -> res.getResVisibility().equals("PUBLIC"));
    }

    @Test
    void linkToUser_userExists() {
        List<Resource> resList = testResourceRepository.findAll();
        long resId = resList.get(0).getResourceId();
        List<User> userList= userRepository.findAll();
        long userId = userList.get(0).getUserId();
        testResourceRepository.linkToUser(
                resId,
                userId
        );
        Assertions.assertThat(
                    testResourceRepository.findById(resId).get().getResourceHolders()
                )
                .filteredOn(user -> user.toString().equals(userRepository.findById(userId).get().toString()));
    }

    @Test
    void deleteResourceById() {
        System.out.println(testResourceRepository.findAll());
        Resource r = testResourceRepository.findAll().get(0);
        testResourceRepository.deleteById(r.getResourceId());
        Assertions.assertThat(testResourceRepository.findById(r.getResourceId()).isPresent())
                .isFalse();
    }
}