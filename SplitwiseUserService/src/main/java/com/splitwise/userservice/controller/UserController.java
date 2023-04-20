package com.splitwise.userservice.controller;


import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.LoginResponse;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = this.userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) {
        User user1=this.userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser_id(user1.getUserID());
        loginResponse.setName(user1.getName());
        loginResponse.setEmail(user1.getEmail());
        return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-users-by-group-id/{groupID}")
    public ResponseEntity<List<User>> getAllUsersInGroup(@PathVariable String groupID) {
        List<User> userList = this.userService.getAllUsersWithGroupID(groupID);
        return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
    }

}
