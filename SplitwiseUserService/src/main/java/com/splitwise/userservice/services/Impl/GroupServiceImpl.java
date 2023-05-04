package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.exceptions.ResourceNotFound;
import com.splitwise.userservice.payload.APIResponse;
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
        User user = this.userRepository.findById(group.getOwnerID()).orElseThrow(() -> new ResourceNotFound("User", "Id"));
        Set<User> userList = new HashSet<>();
        userList.add(user);
        group.setUserList(userList);
        return this.groupRepository.save(group);
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
            System.out.println(e.getMessage());
        }
        return new APIResponse("Unable to exit qroup",false);
    }

    @Override
    public Set<User> getAllUsersByGroupID(String groupID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id" ));
        return group.getUserList();
    }

    @Override
    public APIResponse getGroupDetail(String groupID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id"));
        GroupDetail groupDetail = new GroupDetail();
        // group Info
        groupDetail.setGroup(group);

        // user list
        groupDetail.setUserList(group.getUserList());

        // get expense list of group from expense service

        Set<Expense> expenses = restTemplate.getForObject("http://EXPENSE-SERVICE/api/expense/get-expense-list-by-group-id/"+group.getGroupID(), HashSet.class);
        groupDetail.setExpenseList(expenses);

        return new APIResponse(groupDetail,true);
    }
}
