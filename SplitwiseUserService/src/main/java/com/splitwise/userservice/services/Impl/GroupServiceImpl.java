package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.exceptions.ResourceNotFound;
import com.splitwise.userservice.payload.ExitGroupBody;
import com.splitwise.userservice.payload.Expense;
import com.splitwise.userservice.payload.GroupDetail;
import com.splitwise.userservice.payload.UserListResponse;
import com.splitwise.userservice.repositories.GroupRepository;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;
    @Override
    public Group createGroup(Group group) {
        User user = this.userRepository.findById(group.getOwnerID()).orElseThrow();
        Set<User> userList = new HashSet<>();
        userList.add(user);
        group.setUserList(userList);
        return this.groupRepository.save(group);
    }

    @Override
    public UserListResponse addUserToGroupWithEmailId(String groupID, String emailID) {
        UserListResponse userListResponse = new UserListResponse();
        User user = this.userRepository.findUserByEmail(emailID);
        System.out.println(user.getEmail());
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id" , groupID));
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
        this.groupRepository.deleteById(groupID);
    }

    @Override
    public String exitGroup(ExitGroupBody exitGroupBody) {
        try {
            Group group = this.groupRepository.findById(exitGroupBody.getGroupID()).orElseThrow(() -> new ResourceNotFound("Group", "Id", exitGroupBody.getGroupID()));
            Set<User> userList = group.getUserList();
            for (User user : userList) {
                if (user.getUserID().equals(exitGroupBody.getUserID())) {
                    userList.remove(user);
                    user.getGroupList().remove(group);
                    this.userRepository.save(user);
                    return "success";
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return "failure";
    }

    @Override
    public Set<User> getAllUsersByGroupID(String groupID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id" , groupID));
        return group.getUserList();
    }

    @Override
    public GroupDetail getGroupDetail(String groupID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id" , groupID));
        GroupDetail groupDetail = new GroupDetail();
        // group Info
        groupDetail.setGroup(group);

        // user list
        groupDetail.setUserList(group.getUserList());

        // get expense list of group from expense service

        Set<Expense> expenses = restTemplate.getForObject("http://EXPENSE-SERVICE/api/expense/get-expense-list-by-group-id/"+group.getGroupID(), HashSet.class);
        groupDetail.setExpenseList(expenses);

        return groupDetail;
    }
}
