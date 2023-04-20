package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GroupService {

    Group createGroup(Group group, String ownerId);

    Group addUserToGroupByEmailId(String GroupId, String emailId);

    void deleteGroup(String groupID);

    void exitGroup(String groupID, String userID);

    List<Group> getALlGroup(String userID);

    List<User> getAllUsersWithGroupID(String groupID);
}
