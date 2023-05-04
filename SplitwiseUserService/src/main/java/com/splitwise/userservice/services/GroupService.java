package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.payload.AddUserToGroupBody;
import com.splitwise.userservice.payload.ExitGroupBody;
import com.splitwise.userservice.payload.UserListResponse;

import java.util.Set;

public interface GroupService {

    Group createGroup(Group group);

    APIResponse addUserToGroupWithEmailId(String groupId, String emailId);

    void deleteGroup(String groupID);

    APIResponse exitGroup(ExitGroupBody exitGroupBody);

    Set<User> getAllUsersByGroupID(String groupID);
}
