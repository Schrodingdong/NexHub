package com.example.authentification.appuser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;
import java.util.Random;

@Getter @Setter @ToString
public class MetadataUserModel {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String bucketId;

    public MetadataUserModel(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bucketId = generateBucketIdViaUserMail(this.email);
    }

    public String generateBucketIdViaUserMail(String userMail) {
        String firstPartOfMail = userMail.substring(0,userMail.indexOf('@'));
        // Salt generation
        byte[] array = new byte[15];
        new Random().nextBytes(array);
        String salt = new String(array, Charset.forName("UTF-8"));
        // result string
        String result = userMail+salt;
        result = firstPartOfMail+"-"+Math.abs(result.hashCode());
        return result.toLowerCase();
    }
}
