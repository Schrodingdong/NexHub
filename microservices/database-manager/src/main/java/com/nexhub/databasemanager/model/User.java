package com.nexhub.databasemanager.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
@Getter @Setter @ToString
public class User {
    @Id
    @GeneratedValue
    private long userId;
    @NotBlank
    @NotNull
    private String username;
    @Email
    @NotNull
    private String mail;
    @NotBlank
    @NotNull
    private String bucketId;

//    // TODO
//    @Relationship(type = "FOLLOWS", direction = OUTGOING)
//    private Set<Category> userCategoryFollowing = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = OUTGOING)
    private Set<User> userFollowing = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = INCOMING)
    private Set<User> userFollowers = new HashSet<>();
    @Relationship(type = "HAS_A", direction = OUTGOING)
    private Set<Resource> userResources = new HashSet<>();

    public User(String username, String mail) {
        this.username = username;
        this.mail = mail;
        this.bucketId = generateBucketIdViaUserMail(this.mail);
    }
    public String generateBucketIdViaUserMail(String userMail) {
        if(userMail == null) return null;
        String firstPartOfMail = userMail.substring(0,userMail.indexOf('@'));
        // Salt generation
        byte[] array = new byte[15];
        new Random().nextBytes(array);
        String salt = new String(array, Charset.forName("UTF-8"));
        // result string
        String result = userMail+salt;
        result = firstPartOfMail+"-"+Math.abs(result.hashCode());
        return result;
    }
}
