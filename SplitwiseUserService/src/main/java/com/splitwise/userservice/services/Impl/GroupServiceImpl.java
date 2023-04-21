package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.exceptions.ResourceNotFound;
import com.splitwise.userservice.payload.AddUserToGroup;
import com.splitwise.userservice.payload.UserListResponse;
import com.splitwise.userservice.repositories.GroupRepository;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public Group createGroup(Group group) {
        User user = this.userRepository.findById(group.getOwnerID()).orElseThrow();
        Set<User> userList = new HashSet<>();
        userList.add(user);
        group.setUserList(userList);
        Group group1 = this.groupRepository.save(group);
        return group1;
    }

    @Override
    public UserListResponse addUserToGroupWithEmailId(String groupID, AddUserToGroup emailID) {
        UserListResponse userListResponse = new UserListResponse();
        User user = this.userRepository.findUserByEmail(emailID.getEmailID());
        Group group = this.groupRepository.findById(groupID).orElseThrow();
        if(user != null) {
            Set<User> userList = group.getUserList();
            userList.add(user);
            group.setUserList(userList);
            this.groupRepository.save(group);
            userListResponse.setUsers(userList);
            userListResponse.setStatus("success");
            return userListResponse;
        }
        userListResponse.setStatus("userDoesNotExist");
        return userListResponse;
    }

    @Override
    public void deleteGroup(String groupID) {

    }

    @Override
    public void exitGroup(String groupID, String userID) {

    }

    @Override
    public Set<User> getAllUsersByGroupID(String groupID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow();
        return group.getUserList();
    }
}
