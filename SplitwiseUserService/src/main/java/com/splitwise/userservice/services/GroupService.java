package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.AddUserToGroup;
import com.splitwise.userservice.payload.UserListResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface GroupService {

    Group createGroup(Group group);

    UserListResponse addUserToGroupWithEmailId(String GroupId, AddUserToGroup emailId);

    void deleteGroup(String groupID);

    void exitGroup(String groupID, String userID);

    Set<User> getAllUsersByGroupID(String groupID);
}
