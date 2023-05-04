package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.exceptions.ResourceNotFound;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.payload.AddUserToGroupBody;
import com.splitwise.userservice.payload.ExitGroupBody;
import com.splitwise.userservice.payload.UserListResponse;
import com.splitwise.userservice.repositories.GroupRepository;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public Group createGroup(Group group) {
        User user = this.userRepository.findById(group.getOwnerID()).orElseThrow(() -> new ResourceNotFound("User", "Id"));
        Set<User> userList = new HashSet<>();
        userList.add(user);
        group.setUserList(userList);
        Group group1 = this.groupRepository.save(group);
        return group1;
    }

    @Override
    public APIResponse addUserToGroupWithEmailId(String groupID, String emailID) {
        APIResponse apiResponse = new APIResponse();
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id"));
        try {
            User user = this.userRepository.findUserByEmail(emailID);
            System.out.println(user.getEmail());
            Set<User> userList = group.getUserList();
            userList.add(user);
            group.setUserList(userList);
            this.groupRepository.save(group);
            apiResponse.setObject(userList);
            apiResponse.setSuccess(true);
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setObject("User does not exist");
            apiResponse.setSuccess(false);
            return apiResponse;
        }

    }

    @Override
    public void deleteGroup(String groupID) {
        this.groupRepository.deleteById(groupID);
    }

    @Override
    public APIResponse exitGroup(ExitGroupBody exitGroupBody) {
        try {
            Group group = this.groupRepository.findById(exitGroupBody.getGroupID()).orElseThrow(() -> new ResourceNotFound("Group", "Id"));
            Set<User> userList = group.getUserList();
            for (User user : userList) {
                if (user.getUserID().equals(exitGroupBody.getUserID())) {
                    userList.remove(user);
                    user.getGroupList().remove(group);
                    this.userRepository.save(user);
                    return new APIResponse("Group exited",true);
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return new APIResponse("Unable to exit qroup",false);
    }

    @Override
    public Set<User> getAllUsersByGroupID(String groupID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id" ));
        return group.getUserList();
    }
}
