package com.nexhub.databasemanager.model;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
public class User {
    @Id
    @GeneratedValue
    private final long userId;
    private String username;
    private String mail;

//    @Relationship(type = "HAS_A", direction = OUTGOING)
//    private Set<Resource> userResources = new HashSet<>();
//    @Relationship(type = "FOLLOWS", direction = OUTGOING)
//    private Set<Category> userCategoryFollowing = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = OUTGOING)
    private Set<User> userFollowing = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = INCOMING)
    private Set<User> userFollowers = new HashSet<>();


    public User(long userId, String username, String mail) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", userFollowing=" + userFollowing +
                ", userFollowers=" + userFollowers +
                '}';
    }
}
