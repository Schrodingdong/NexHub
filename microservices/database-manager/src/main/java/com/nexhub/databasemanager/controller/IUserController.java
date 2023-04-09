package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserController {
    void addUser(User newUser);
    Optional<User> getUser(long userId);
    List<User> getAllUsers();
    void deleteUser(long userId);
    List<User> getAllUsersOfName(String name);
    void updateUser(long userId, User modifiedUser);
}
