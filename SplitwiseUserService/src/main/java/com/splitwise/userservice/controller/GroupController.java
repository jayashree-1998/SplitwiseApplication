package com.splitwise.userservice.controller;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
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

    @PostMapping("/create-group/{userid}")
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @PathVariable("userid") String OwnerId) {
        Group group1 = this.groupService.createGroup(group, OwnerId);
        return ResponseEntity.status(HttpStatus.OK).body(group1);
    }

    @GetMapping("/get-all-users-by-group-id/{groupID}")
    public ResponseEntity<Set<User>> getAllUsersInGroup(@PathVariable String groupID) {
        Set<User> userList = this.groupService.getAllUsersByGroupID(groupID);
        return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
    }
}
