package com.schrodingdong.resourcemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;
import java.util.Random;


@Getter @Setter @ToString
@AllArgsConstructor
public class UserModel {
    private long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String bucketId;


}
