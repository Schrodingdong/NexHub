package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserController {
    public void addUser(User newUser);
    public User getUser(String userId);
    public List<User> getUserFollowings(String userId);
    public List<User> getUserFollowers(String userId);
}
