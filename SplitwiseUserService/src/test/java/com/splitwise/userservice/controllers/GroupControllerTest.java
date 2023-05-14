package com.splitwise.userservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.splitwise.userservice.controller.GroupController;
import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.payload.AddUserToGroupBody;
import com.splitwise.userservice.payload.UserGroupBody;
import com.splitwise.userservice.services.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GroupService groupService;

    @Test
    public void createGroupAPITest() throws Exception {
        Group group = new Group("group1", "SPE Group", "user1", false, new HashSet<>());

        when(groupService.createGroup(group)).thenReturn(group);
        this.mockMvc.perform(post("/group/create-group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getGroupDetailAPITest() throws Exception {
        Group group = new Group("group1", "SPE Group", "user1", false, new HashSet<>());
        APIResponse successResponse = new APIResponse("", true);

        when(groupService.getGroupDetail(group.getGroupID())).thenReturn(successResponse);

        this.mockMvc.perform(get("/group/get-group-detail/{groupID}", "group1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void getAllUsersInGroupAPITest() throws Exception {
        User user1 = new User("user1", "user1", "user1@gmail.com", "user1", "7878564534", new HashSet<>());
        User user2 = new User("user2", "user2", "user2@gmail.com", "user2", "9876469390", new HashSet<>());
        HashSet<User> userSet = new HashSet<>(Set.of(user1, user2));
        Group group = new Group("group1", "SPE Group", "user1", false, userSet);


        APIResponse successResponse = new APIResponse(userSet, true);

        when(groupService.getAllUsersByGroupID(group.getGroupID())).thenReturn(userSet);

        this.mockMvc.perform(get("/group/get-all-users-by-group-id/{groupID}", "group1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.object.size()", is(2)));
    }

    @Test
    public void addUserToGroupTest() throws Exception {
        AddUserToGroupBody addUserToGroupBody = new AddUserToGroupBody("user@gmail.com", "group1");
        APIResponse apiResponse = new APIResponse("this is test response", true);
        when(groupService.addUserToGroupWithEmailId(addUserToGroupBody.getGroupID(), addUserToGroupBody.getEmailID())).thenReturn(apiResponse);
        this.mockMvc.perform(post("/group/add-user-to-group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addUserToGroupBody)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void deleteGroupAPITest() throws Exception {
        UserGroupBody userGroupBody = new UserGroupBody("group1", "user1");
        APIResponse apiResponse = new APIResponse("this is test object", true);

        when(groupService.deleteGroup(userGroupBody.getGroupID(), userGroupBody.getUserID())).thenReturn(apiResponse);

        this.mockMvc.perform(delete("/group/delete-group")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userGroupBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void settleGroupAPITest() throws Exception {
        APIResponse apiResponse = new APIResponse("this is test object", true);
        when(groupService.settleGroup("group1")).thenReturn(apiResponse);
        this.mockMvc.perform(post("/group/make-group-settle/{groupID}", "group1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }
}
