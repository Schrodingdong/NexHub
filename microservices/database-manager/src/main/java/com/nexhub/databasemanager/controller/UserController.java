package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController implements IUserController{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public User addUser(@RequestBody @NotNull @Valid User newUser) throws BadRequestException {
        return userService.saveUser(newUser);
    }

    @Override
    @PutMapping("/{userId}/follows/{followId}")
    @ResponseStatus(HttpStatus.OK)
    public void followUser(@PathVariable @NotNull long userId, @PathVariable @NotNull long followId) {
        userService.followUser(userId, followId);
    }

    @Override
    @GetMapping("/get/id/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable @NotNull @Valid long userId) {
        return userService.getUserById(userId);
    }

    @Override
    @GetMapping("/get/mail/{mail}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByMail(@PathVariable String mail) {
        return userService.getUserByMail(mail);
    }

    @Override
    @GetMapping("/get/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    @GetMapping("/get/{userId}/following")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserFollowing(@PathVariable long userId) {
        return userService.getUserFollowing(userId);
    }

    @Override
    @GetMapping("/get/{userId}/followers")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUserFollowers(@PathVariable long userId) {
        return userService.getUserFollowers(userId);
    }

    @Override
    @DeleteMapping("/delete/id/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable @NotNull @Valid long userId) {
        userService.deleteUser(userId);
    }

    @Override
    @DeleteMapping("/delete/all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAll(){
        userService.deleteAll();
    }

    @Override
    @GetMapping("/get/username/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsersOfName(@PathVariable @NotNull @Valid String name) {
        return userService.getUsersByName(name);
    }

    @Override
    @PutMapping("/update/id/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable @NotNull long userId,@RequestBody User modifiedUser) {
       return userService.updateUser(userId, modifiedUser);
    }


}
