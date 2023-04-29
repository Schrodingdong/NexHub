package com.nexhub.databasemanager.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class ResourceTest {
    private Resource r,r2;
    private User u;

    @BeforeEach
    void setUp() {

        u = new User(
                "schrodingdong",
                "schrodingdong@gmail.com","bucketId2"
        );
        Set<User> userSet=new HashSet<>();

        r = new Resource(
                "Guide To java",
                "Simple guide for java haha",
                ResVisibility.PUBLIC.name()
        );
        r2 = new Resource(
                "Guide To java",
                "Simple guidefjdsqolifj for java haha",
                ResVisibility.PUBLIC.name()
        );
    }

    @Test
    void checkResBucketIdUnicity(){
        System.out.println(r.getResourceBucketId());
        System.out.println(r2.getResourceBucketId());
        Assertions.assertThat(r.getResourceBucketId())
                .isNotEqualTo(r2.getResourceBucketId());
    }

}