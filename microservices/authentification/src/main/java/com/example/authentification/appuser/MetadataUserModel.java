package com.example.authentification.appuser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;
import java.util.Random;

@Getter @Setter @ToString
public class MetadataUserModel {
    private String username;
    private String mail;
    private String bucketId;

    public MetadataUserModel(String username, String mail) {
        this.username = username;
        this.mail = mail;
        this.bucketId = generateBucketIdViaUserMail(this.mail);
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
