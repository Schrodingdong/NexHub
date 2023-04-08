package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;

import java.util.List;

public class DatabaseManager implements IDatabaseManager{

    @Override
    public void addResourceToUser(String userId, Resource resource) {

    }

    @Override
    public List<Resource> getAllUserResources(String userId) {
        return null;
    }

    @Override
    public Resource getUserResource(String userId, String resourceId) {
        return null;
    }

    @Override
    public List<Resource> getUserResourceFromTopic(String userId, String topicName) {
        return null;
    }

    @Override
    public void addUser(User newUser) {

    }

    @Override
    public User getUser(String userId) {
        return null;
    }

    @Override
    public List<User> getUserFollowings(String userId) {
        return null;
    }

    @Override
    public List<User> getUserFollowers(String userId) {
        return null;
    }
}
