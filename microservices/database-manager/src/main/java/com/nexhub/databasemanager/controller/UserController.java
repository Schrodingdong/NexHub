package com.nexhub.databasemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController implements IUserController{
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @NotNull @Valid User newUser) throws BadRequestException {
        User u = userService.saveUser(newUser);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("userId",u.getUserId())
                        .put("username",u.getUsername())
                        .put("firstName",u.getFirstName())
                        .put("lastName",u.getLastName())
                        .put("email",u.getEmail())
                        .put("bucketId",u.getBucketId())
        );
    }

    @Override
    @PutMapping("/{userId}/follows/{followId}")
    public ResponseEntity<?> followUser(@PathVariable @NotNull long userId, @PathVariable @NotNull long followId) {
        userService.followUser(userId, followId);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("userId",userId)
                        .put("followingId",followId)
        );
    }

    @Override
    @DeleteMapping("/{userId}/unfollows/{toUnfollow}")
    public ResponseEntity<?> unfollowUser(@PathVariable @NotNull long userId, @PathVariable @NotNull long toUnfollow) {
        userService.unfollowUser(userId, toUnfollow);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("userId",userId)
                        .put("unfollowedId",toUnfollow)
        );
    }

    @Override
    @GetMapping("/get/id/{userId}")
    public ResponseEntity<?> getUser(@PathVariable @NotNull @Valid long userId) {
        User userById = userService.getUserById(userId);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("userId",userById.getUserId())
                        .put("firstName",userById.getFirstName())
                        .put("lastName",userById.getLastName())
                        .put("email",userById.getEmail())
                        .put("bucketId",userById.getBucketId())
        );
    }

    @Override
    @GetMapping("/get/mail/{mail}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUserByMail(@PathVariable String mail) {
        User userByMail = userService.getUserByMail(mail);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("userId",userByMail.getUserId())
                        .put("username",userByMail.getUsername())
                        .put("firstName",userByMail.getFirstName())
                        .put("lastName",userByMail.getLastName())
                        .put("email",userByMail.getEmail())
                        .put("bucketId",userByMail.getBucketId())
        );
    }

    @Override
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllUsers() throws JsonProcessingException {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(
                allUsers
        );
    }

    @Override
    @GetMapping("/get/{userId}/following")
    public ResponseEntity<?> getUserFollowing(@PathVariable long userId) throws JsonProcessingException {
        List<User> userFollowing = userService.getUserFollowing(userId);
        return ResponseEntity.ok().body(
                userFollowing
        );
    }

    @Override
    @GetMapping("/get/{userId}/followers")
    public ResponseEntity<?> getUserFollowers(@PathVariable long userId) throws JsonProcessingException {
        List<User> userFollowers = userService.getUserFollowers(userId);
        return ResponseEntity.ok().body(
                userFollowers
        );
    }

    @Override
    @DeleteMapping("/delete/id/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable @NotNull @Valid long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.accepted().body(
                objectMapper.createObjectNode()
                        .put("userId",userId)
        );
    }

    @Override
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll(){
        userService.deleteAll();
        return ResponseEntity.accepted().body(
                objectMapper.createObjectNode()
                        .put("message","All users deleted")
        );
    }

    @Override
    @GetMapping("/get/username/{name}")
    public ResponseEntity<?> getAllUsersOfName(@PathVariable @NotNull @Valid String name) throws JsonProcessingException {
        List<User> usersByName = userService.getUsersByName(name);
        return ResponseEntity.ok().body(
                usersByName
        );
    }

    @Override
    @PutMapping("/update/id/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable @NotNull long userId, @RequestBody User modifiedUser) {
        if(modifiedUser.getEmail() != null){
            return ResponseEntity.badRequest().body(
                    objectMapper.createObjectNode()
                            .put("message","Email cannot be changed")
            );
        }
        if(modifiedUser.getBucketId() != null){
            return ResponseEntity.badRequest().body(
                    objectMapper.createObjectNode()
                            .put("message","bucketId cannot be changed")
            );
        }
        User user = userService.updateUser(userId, modifiedUser);

        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("userId",user.getUserId())
                        .put("username",user.getUsername())
                        .put("firstName",user.getFirstName())
                        .put("lastName",user.getLastName())
                        .put("email",user.getEmail())
                        .put("bucketId",user.getBucketId())
        );
    }


}
