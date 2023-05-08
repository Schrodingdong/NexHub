package com.nexhub.databasemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexhub.databasemanager.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserController {
    ResponseEntity<?> addUser(User newUser) throws Exception;
    ResponseEntity<?> followUser(long userId, long followId);
    ResponseEntity<?> unfollowUser(long userId, long toUnfollow);
    ResponseEntity<?> getUser(long userId);
    ResponseEntity<?> getUserByMail(String mail);
    ResponseEntity<?> getAllUsers() throws JsonProcessingException;
    ResponseEntity<?> getUserFollowing(long userId) throws JsonProcessingException;
    ResponseEntity<?> getUserFollowers(long userId) throws JsonProcessingException;
    ResponseEntity<?> updateUser(long userId, User modifiedUser);
    ResponseEntity<?> deleteUser(long userId);
    ResponseEntity<?> deleteAll();
    ResponseEntity<?> getAllUsersOfName(String name) throws JsonProcessingException;
}
