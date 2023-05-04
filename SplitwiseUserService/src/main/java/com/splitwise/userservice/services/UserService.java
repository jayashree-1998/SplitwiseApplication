package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.APIResponse;

import java.util.Set;

public interface UserService {

    APIResponse registerUser(User user);
    User findUserByEmailAndPassword(String email, String password);

    Set<Group> getAllGroupsByUserID(String userID);
}
