package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsersByName(String name){
        return userRepository.getUsersByName(name);
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public long getUserId(@NonNull User u){
        return u.getUserId();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User u){
        userRepository.save(u);
    }
    public void deleteUser(long userId){
        userRepository.deleteById(userId);
    }
}
