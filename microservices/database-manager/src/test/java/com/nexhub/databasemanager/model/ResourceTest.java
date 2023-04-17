package com.nexhub.databasemanager.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
                69,
                "schrodingdong",
                "schrodingdong@gmail.com"
        );
        Set<User> userSet=new HashSet<>();

        r = new Resource(
                0,
                "Guide To java",
                "Simple guide for java haha",
                "auo",
                ResVisibility.PUBLIC.name()
        );
        r2 = new Resource(
                0,
                "Guide To java",
                "Simple guidefjdsqolifj for java haha",
                "joe biden wake up",
                ResVisibility.PUBLIC.name()
        );
    }

    @Test
    void checkResBucketIdUnicity(){
        System.out.println(r.getResBucketId());
        System.out.println(r2.getResBucketId());
        Assertions.assertThat(r.getResBucketId())
                .isNotEqualTo(r2.getResBucketId());
    }

}