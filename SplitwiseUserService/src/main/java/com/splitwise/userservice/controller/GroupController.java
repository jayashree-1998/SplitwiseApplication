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
    public ResponseEntity<APIResponse> createGroup(@RequestBody Group group) {
        Group group1 = this.groupService.createGroup(group);
        return new ResponseEntity<>(new APIResponse(group1,true), HttpStatus.CREATED);
    }

    @GetMapping("/get-group-detail/{groupID}")
    public ResponseEntity<APIResponse> getGroupDetail(@PathVariable String groupID ) {
        APIResponse apiResponse = this.groupService.getGroupDetail(groupID);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-users-by-group-id/{groupID}")
    public ResponseEntity<APIResponse> getAllUsersInGroup(@PathVariable String groupID) {
        Set<User> userList = this.groupService.getAllUsersByGroupID(groupID);
        return new ResponseEntity<>(new APIResponse(userList, true),HttpStatus.ACCEPTED);
    }


    @PostMapping("/add-user-to-group")
    public ResponseEntity<APIResponse> addUserToGroup(@RequestBody AddUserToGroupBody addUserToGroupBody) {
        APIResponse apiResponse = this.groupService.addUserToGroupWithEmailId(addUserToGroupBody.getGroupID(), addUserToGroupBody.getEmailID());
        return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-group/{groupID}")
    public ResponseEntity<APIResponse> deleteGroup(@PathVariable String groupID) {
        this.groupService.deleteGroup(groupID);
        return new ResponseEntity(new APIResponse("Group deleted successfully!", true), HttpStatus.OK);
    }

    @PostMapping("/exit-group")
    public ResponseEntity<APIResponse> exitGroup(@RequestBody ExitGroupBody exitGroupBody) {
        APIResponse apiResponse = this.groupService.exitGroup(exitGroupBody);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

}
