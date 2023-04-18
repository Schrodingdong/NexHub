package com.nexhub.databasemanager.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserTest {
    User user;
    @BeforeEach
    void setUp() {
        user = new User(
                "schrodingdong",
                "schrodingdong@gmail.com"
        );
    }


    @Test
    void setUsername(){
        String newUsername = "newUsername";
        user.setUsername(newUsername);
        Assertions.assertThat(user.getUsername()).isEqualTo(newUsername);
    }

    @Test
    void setMail(){
        String newMail = "newMail";
        user.setMail(newMail);
        Assertions.assertThat(user.getMail()).isEqualTo(newMail);
    }
}