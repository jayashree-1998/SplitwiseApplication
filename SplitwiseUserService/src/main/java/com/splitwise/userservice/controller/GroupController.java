package com.splitwise.userservice.controller;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.AddUserToGroup;
import com.splitwise.userservice.payload.UserListResponse;
import com.splitwise.userservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired GroupService groupService;

    @PostMapping("/create-group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group group1 = this.groupService.createGroup(group);
        return ResponseEntity.status(HttpStatus.OK).body(group1);
    }

    @GetMapping("/get-all-users-by-group-id/{groupID}")
    public ResponseEntity<Set<User>> getAllUsersInGroup(@PathVariable String groupID) {
        Set<User> userList = this.groupService.getAllUsersByGroupID(groupID);
        return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
    }


    @PostMapping("/add-user-to-group/{groupID}")
    public ResponseEntity<UserListResponse> addUserToGroup(@RequestBody AddUserToGroup emailID , @PathVariable String groupID) {
        UserListResponse userList = this.groupService.addUserToGroupWithEmailId(groupID,emailID);
        return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
    }

}
