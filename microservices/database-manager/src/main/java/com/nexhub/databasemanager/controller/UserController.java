package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import jdk.jfr.ContentType;
import org.springframework.http.HttpStatus;
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
        try {
            userService.saveUser(newUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @GetMapping("/get/id/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUser(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @Override
    @GetMapping("/get/all")
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
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
        } catch(Exception e){
            try {
                userService.saveUser(modifiedUser);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
