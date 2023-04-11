package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.OK)
    public User addUser(@RequestBody @NotNull @Valid User newUser) throws BadRequestException {
        return userService.saveUser(newUser);
    }

    @Override
    @GetMapping("/get/id/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable @NotNull @Valid long userId) {
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
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteUser(@PathVariable @NotNull @Valid long userId) {
        userService.deleteUser(userId);
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
    public User updateUser(@PathVariable @NotNull long userId,@RequestBody @Valid User modifiedUser) {
       return userService.updateUser(userId, modifiedUser);
    }


}
