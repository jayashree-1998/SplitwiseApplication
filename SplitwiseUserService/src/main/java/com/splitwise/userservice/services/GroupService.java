package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.payload.UserGroupBody;

import java.util.Set;

public interface GroupService {

    Group createGroup(Group group);

    APIResponse addUserToGroupWithEmailId(String groupId, String emailId);

    APIResponse deleteGroup(String groupID, String ownerID);

    APIResponse exitGroup(UserGroupBody exitGroupBody);

    Set<User> getAllUsersByGroupID(String groupID);

    APIResponse getGroupDetail(String groupID);

    APIResponse settleGroup(String groupID);
}
