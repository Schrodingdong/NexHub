package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.User;

import java.util.List;

public interface IUserController {
    User addUser(User newUser) throws Exception;
    User getUser(long userId);
    List<User> getAllUsers();
    void deleteUser(long userId);
    List<User> getAllUsersOfName(String name);
    User updateUser(long userId, User modifiedUser);
}
