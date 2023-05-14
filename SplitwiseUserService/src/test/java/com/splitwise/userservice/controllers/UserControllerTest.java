package com.splitwise.userservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.splitwise.userservice.controller.UserController;
import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.services.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void registerAPITest() throws Exception{
        User user = new User("user1", "user", "user@gmail.com", "user", "1122334455", new HashSet<>());
        APIResponse successResponse = new APIResponse("You have been successfully registered. Please login to continue!", true);

        when(userService.registerUser(user)).thenReturn(successResponse);

        this.mockMvc.perform(post("/user/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void loginTest() throws Exception {
        User user = new User("user1", "user", "user@gmail.com", "user", "1122334455", new HashSet<>());
        when(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);

        this.mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.object.name", is("user")));
    }

    @Test
    public void getAllGroupByUserIDTest() throws Exception {
        User user1 = new User("user1", "user1", "user1@gmail.com", "user1", "1122334455", new HashSet<>());
        Set<Group> groupSet = Set.of(new Group("group1", "SPE", "user1", false, new HashSet<>()), new Group("group2", "FSL", "user2", false, new HashSet<>()));
        user1.setGroupList(groupSet);

        when(userService.getAllGroupsByUserID("user1")).thenReturn(groupSet);

        this.mockMvc.perform(get("/user/get-all-groups-by-user-id/user1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.object.size()", is(2)));
    }

}


