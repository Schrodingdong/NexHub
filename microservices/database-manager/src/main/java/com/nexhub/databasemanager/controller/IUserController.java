package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IUserController {
    User addUser(User newUser) throws Exception;
    User getUser(long userId);
    User getUserByMail(String mail);
    List<User> getAllUsers();
    List<Resource> getAllResourcesFromUser(long userId);
    List<Resource> getAllPublicResourcesFromUser(long userId);
    User updateUser(long userId, User modifiedUser);
    void deleteUser(long userId);
    List<User> getAllUsersOfName(String name);
}
