package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;

import java.util.List;

public interface IDatabaseManager {
    public void addResourceToUser(String userId, Resource resource);
    public List<Resource> getAllUserResources(String userId);
    public Resource getUserResource(String userId, String resourceId);
    public List<Resource> getUserResourceFromTopic(String userId, String topicName);

    public void addUser(User newUser);
    public User getUser(String userId);
    public List<User> getUserFollowings(String userId);
    public List<User> getUserFollowers(String userId);
}
