package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);
    User findUserByEmailAndPassword(String email, String password);
}
