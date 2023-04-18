package com.nexhub.databasemanager.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
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

    @Relationship(type = "HAS_A", direction = OUTGOING)
    private Set<Resource> userResources = new HashSet<>();

    // TODO
//    @Relationship(type = "FOLLOWS", direction = OUTGOING)
//    private Set<Category> userCategoryFollowing = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = OUTGOING)
    private Set<User> userFollowing = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = INCOMING)
    private Set<User> userFollowers = new HashSet<>();


    public User(String username, String mail) {
        this.username = username;
        this.mail = mail;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Resource> getUserResources() {
        return userResources;
    }

    public void setUserResources(Set<Resource> userResources) {
        this.userResources = userResources;
    }

    public Set<User> getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(Set<User> userFollowing) {
        this.userFollowing = userFollowing;
    }

    public Set<User> getUserFollowers() {
        return userFollowers;
    }

    public void setUserFollowers(Set<User> userFollowers) {
        this.userFollowers = userFollowers;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", userResources=" + userResources +
                ", userFollowing=" + userFollowing +
                ", userFollowers=" + userFollowers +
                '}';
    }
}
