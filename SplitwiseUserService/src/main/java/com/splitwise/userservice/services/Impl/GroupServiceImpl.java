package com.splitwise.userservice.services.Impl;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.exceptions.ResourceNotFound;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.payload.UserGroupBody;
import com.splitwise.userservice.payload.Expense;
import com.splitwise.userservice.payload.GroupDetail;
import com.splitwise.userservice.repositories.GroupRepository;
import com.splitwise.userservice.repositories.UserRepository;
import com.splitwise.userservice.services.GroupService;
import com.splitwise.userservice.services.externalServices.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ExpenseService expenseService;

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

    @Transactional
    @Override
    public APIResponse deleteGroup(String groupID, String ownerID) {
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id"));

        APIResponse apiResponse = new APIResponse();
        try {
            if(group.getOwnerID().compareTo(ownerID) == 0) {


                // if group is successfully deleted, we can delete expenses for this group in expense service
                try {
                    APIResponse response = expenseService.deleteExpensesOfGroup(groupID);
                    if(response.getSuccess()) {
                        apiResponse.setObject("Group and expenses deleted!");
                        apiResponse.setSuccess(true);
                    } else {
                        apiResponse.setObject("Error in deleting expenses");
                        apiResponse.setSuccess(false);
                    }
                } catch (Exception e) {
                    apiResponse.setObject("error in deleting expenses");
                    apiResponse.setSuccess(false);
                    return apiResponse;
                }

                // delete group
                this.groupRepository.deleteById(groupID);
            } else {
                apiResponse.setObject("Only owner can delete a group!");
                apiResponse.setSuccess(false);
            }
            return apiResponse;
        } catch(Exception e) {
            apiResponse.setObject("Error, deleting group!");
            apiResponse.setSuccess(false);
            return apiResponse;
        }
    }

    @Override
    public APIResponse exitGroup(UserGroupBody exitGroupBody) {
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

        Set<Expense> expenses = expenseService.getExpenses(group.getGroupID());

        groupDetail.setExpenseList(expenses);

        return new APIResponse(groupDetail,true);
    }

    @Override
    public APIResponse settleGroup(String groupID) {
        APIResponse apiResponse = new APIResponse();
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id"));
        try {
            group.setSettled(true);
            this.groupRepository.save(group);
            apiResponse.setSuccess(true);
            apiResponse.setObject("Settled!");
            return apiResponse;
        } catch (Exception e) {
            apiResponse.setObject("Not able to settle group");
            apiResponse.setSuccess(false);
            return apiResponse;
        }
    }
}
