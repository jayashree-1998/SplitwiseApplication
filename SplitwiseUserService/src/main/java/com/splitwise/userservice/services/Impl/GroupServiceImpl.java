package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
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
    public Group createGroup(Group group,String ownerId) {
        User user = this.userRepository.findById(ownerId).orElseThrow();
        group.setOwnerID(ownerId);
        Set<User> userList = new HashSet<>();
        userList.add(user);
        group.setUserList(userList);
        Group group1 = this.groupRepository.save(group);
        return group1;
    }

    @Override
    public Group addUserToGroupByEmailId(String GroupId, String emailID) {
        //find the group
        //find the
        return null;
    }


    @Override
    public void deleteGroup(String groupID) {

    }

    @Override
    public void exitGroup(String groupID, String userID) {

    }

    @Override
    public List<Group> getALlGroup(String userID) {
        return null;
    }

    @Override
    public Set<User> getAllUsersByGroupID(String groupID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow();
        return group.getUserList();
    }
}
