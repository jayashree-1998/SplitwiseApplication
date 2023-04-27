package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.AddUserToGroupBody;
import com.splitwise.userservice.payload.ExitGroupBody;
import com.splitwise.userservice.payload.GroupDetail;
import com.splitwise.userservice.payload.UserListResponse;

import java.util.Set;

public interface GroupService {

    Group createGroup(Group group);

    UserListResponse addUserToGroupWithEmailId(String groupId, String emailId);

    void deleteGroup(String groupID);

    String exitGroup(ExitGroupBody exitGroupBody);

    Set<User> getAllUsersByGroupID(String groupID);

    GroupDetail getGroupDetail(String groupID);
}
