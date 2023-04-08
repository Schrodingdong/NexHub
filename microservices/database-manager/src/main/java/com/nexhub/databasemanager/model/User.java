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
    private final String userId;
    @Property("username")
    private String username;
    @Property("mail")
    private String mail;
    @Relationship(type = "HAS_A", direction = OUTGOING)
    private Set<Resource> userResources = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = OUTGOING)
    private Set<User> userFollowing = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = INCOMING)
    private Set<User> userFollowers = new HashSet<>();
    @Relationship(type = "FOLLOWS", direction = OUTGOING)
    private Set<Category> userCategoryFollowing = new HashSet<>();

    public User(String userId, String username, String mail) {
        this.userId = userId;
        this.username = username;
        this.mail = mail;
    }

    public String getUserId() {
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
}
