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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ExpenseService expenseService;

    @Override
    public Group createGroup(Group group) {
        User user = this.userRepository.findById(group.getOwnerID()).orElseThrow(() -> new ResourceNotFound("User", "Id"));
        Set<User> userList = new HashSet<>();
        userList.add(user);
        group.setUserList(userList);
        logger.info("'{}' ${}$ &{}& *{}* #{}# - Group Created by user ",user.getEmail(),"",user.getUserID(),"","");
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
            logger.info("'{}' ${}$ &{}& *{}* #{}# - User successfully added to group",user.getEmail(),"",user.getUserID(),"","");
            return apiResponse;
        } catch (Exception e) {
            logger.error("'{}' ${}$ &{}& *{}* #{}# - Could not add user to group, user not found",emailID,"","","","");
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
                        logger.info("'{}' ${}$ &{}& *{}* #{}# - Expense deleted while deleting group by owner","","",group.getOwnerID(),"","");
                        apiResponse.setObject("Group and expenses deleted!");
                        apiResponse.setSuccess(true);
                    } else {
                        logger.error("'{}' ${}$ &{}& *{}* #{}# - Error in deleting expenses while deleting group by owner","","",group.getOwnerID(),"","");
                        apiResponse.setObject("Error in deleting expenses");
                        apiResponse.setSuccess(false);
                    }
                } catch (Exception e) {
                    logger.error("'{}' ${}$ &{}& *{}* #{}# - Error in deleting expenses while deleting group by owner","","",group.getOwnerID(),"","");
                    apiResponse.setObject("error in deleting expenses");
                    apiResponse.setSuccess(false);
                    return apiResponse;
                }

                // delete group
                this.groupRepository.deleteById(groupID);
            } else {
                logger.error("'{}' ${}$ &{}& *{}* #{}# - Only owner can delete a group!","","","","","");
                apiResponse.setObject("Only owner can delete a group!");
                apiResponse.setSuccess(false);
            }
            return apiResponse;
        } catch(Exception e) {
            logger.error("'{}' ${}$ &{}& *{}* #{}# - Error in deleting group!","","","","","");
            apiResponse.setObject("Error deleting group!");
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
                    logger.info("'{}' ${}$ &{}& *{}* #{}# - Group exited","","","","","");
                    return new APIResponse("Group exited",true);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        logger.error("'{}' ${}$ &{}& *{}* #{}# - Error in exiting group!","","","","","");
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

        logger.info("'{}' ${}$ &{}& *{}* #{}# - Group Detail successfully populated and returned","","",group.getOwnerID(),"","");
        return new APIResponse(groupDetail,true);
    }

    @Override
    public APIResponse settleGroup(String groupID) {
        APIResponse apiResponse = new APIResponse();
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new ResourceNotFound("Group", "Id"));
        try {
            this.groupRepository.save(group);
            apiResponse.setSuccess(true);
            logger.info("'{}' ${}$ &{}& *{}* #{}# - Group Settled","","","","","");
            apiResponse.setObject("Settled!");
            return apiResponse;
        } catch (Exception e) {
            logger.error("'{}' ${}$ &{}& *{}* #{}# - Not able to settle group","","","","","");
            apiResponse.setObject("Not able to settle group");
            apiResponse.setSuccess(false);
            return apiResponse;
        }
    }
}
