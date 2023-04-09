package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController implements IUserController{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    @PutMapping("/add")
    public void addUser(@RequestBody User newUser) {
        userService.saveUser(newUser);
    }

    @Override
    @GetMapping("/get/id/{userId}")
    public Optional<User> getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @Override
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    @DeleteMapping("/delete/id/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    @Override
    @GetMapping("/get/username/{name}")
    public List<User> getAllUsersOfName(@PathVariable String name) {
        return userService.getUsersByName(name);
    }

    @Override
    @PutMapping("/update/id/{userId}")
    public void updateUser(@PathVariable long userId,@RequestBody User modifiedUser) {
        User oldUser = null;
        String newUsername;
        String newMail;
        try{
            oldUser = userService.getUserById(userId).get();

            newUsername = modifiedUser.getUsername();
            newMail = modifiedUser.getMail();

            oldUser.setUsername(
                    (newUsername == null)? oldUser.getUsername() : newUsername
            );
            oldUser.setMail(
                    (newMail == null)? oldUser.getMail() : newMail
            );
            userService.saveUser(oldUser);
        } catch(NoSuchElementException e){
            userService.saveUser(modifiedUser);
        }
    }


}
