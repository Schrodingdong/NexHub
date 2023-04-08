package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController implements IUserController{
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PutMapping("/add")
    public void addUser(@RequestBody User newUser) {
        userRepository.save(newUser);
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
