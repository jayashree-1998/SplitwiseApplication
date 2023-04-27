package com.splitwise.userservice.controller;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.*;
import com.splitwise.userservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-group-detail/{groupID}")
    public ResponseEntity<GroupDetail> getGroupDetail(@PathVariable String groupID ) {
        GroupDetail groupDetail = this.groupService.getGroupDetail(groupID);
        return new ResponseEntity<>(groupDetail, HttpStatus.OK);
    }

    @GetMapping("/get-all-users-by-group-id/{groupID}")
    public ResponseEntity<Set<User>> getAllUsersInGroup(@PathVariable String groupID) {
        Set<User> userList = this.groupService.getAllUsersByGroupID(groupID);
        return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
    }


    @PostMapping("/add-user-to-group")
    public ResponseEntity<UserListResponse> addUserToGroup(@RequestBody AddUserToGroupBody addUserToGroupBody) {
        UserListResponse userList = this.groupService.addUserToGroupWithEmailId(addUserToGroupBody.getGroupID(), addUserToGroupBody.getEmailID());
        return new ResponseEntity<>(userList,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-group/{groupID}")
    public ResponseEntity<ApiResponse> deleteGroup(@PathVariable String groupID) {
        this.groupService.deleteGroup(groupID);
        return new ResponseEntity(new ApiResponse("group deleted successfully!", true), HttpStatus.OK);
    }

    @PostMapping("/exit-group")
    public ResponseEntity<ApiResponse> exitGroup(@RequestBody ExitGroupBody exitGroupBody) {
        String s = this.groupService.exitGroup(exitGroupBody);
        return new ResponseEntity<>(new ApiResponse(s,true),HttpStatus.OK);
    }

}
