package com.splitwise.userservice.controller;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.payload.LoginResponse;
import com.splitwise.userservice.services.UserService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<APIResponse> registerUser(@RequestBody User user) {
        APIResponse apiResponse = this.userService.registerUser(user);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody User user) {
        try {
            User user1=this.userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUser_id(user1.getUserID());
            loginResponse.setName(user1.getName());
            loginResponse.setEmail(user1.getEmail());
            loginResponse.setMobileNumber(user1.getMobileNumber());
            loginResponse.setGroupList(user1.getGroupList());
            logger.info("'{}' ${}$ &{}& *{}* #{}# - User successfully logged in",user1.getEmail(),"",user1.getUserID(),"","");
            return new ResponseEntity<>(new APIResponse(loginResponse, true), HttpStatus.ACCEPTED);
        } catch(Exception e) {
            System.out.println("Invalid email or password!");
            logger.error("'{}' ${}$ &{}& *{}* #{}# - Login failed,Invalid email or password", user.getEmail(),"","","","");
            return new ResponseEntity<>(new APIResponse("Invalid email or password", false), HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/get-all-groups-by-user-id/{userID}")
    public ResponseEntity<APIResponse>getAllGroupsByUserID(@PathVariable String userID) {
        Set<Group> groupList = this.userService.getAllGroupsByUserID(userID);
        return new ResponseEntity<>(new APIResponse(groupList,true),HttpStatus.ACCEPTED);
    }
}
