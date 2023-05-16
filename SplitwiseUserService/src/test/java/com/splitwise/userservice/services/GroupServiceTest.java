package com.splitwise.userservice.services;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.payload.UserGroupBody;
import com.splitwise.userservice.repositories.GroupRepository;
import com.splitwise.userservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @MockBean
    private GroupRepository groupRepository;

    @MockBean
    UserRepository userRepository;

    @Test
    public void createGroupTest() throws Exception {
        User user = new User("user1", "shashidhar", "shashi@gmail.com", "shashi", "9637355303", new HashSet<>());
        Group group = new Group("group1", "SPE Group", user.getUserID(), true, new HashSet<>());

        when(userRepository.findById("user1")).thenReturn(Optional.of(user));
        when(groupRepository.save(group)).thenReturn(group);

        assertEquals("SPE Group", groupService.createGroup(group).getName());
    }

    @Test
    public void addUserToGroupWithEmailIdTest() throws Exception{
        User user = new User("user1", "shashidhar", "shashi@gmail.com", "shashi", "9637355303", new HashSet<>());

        Group group = new Group("group1", "SPE Group", "user1", true, new HashSet<>());

        when(groupRepository.findById("group1")).thenReturn(Optional.of(group));
        when(userRepository.findUserByEmail("shashi@gmail.com")).thenReturn(user);

        when(groupRepository.save(group)).thenReturn(group);
        Set<User> userList = (Set<User>) groupService.addUserToGroupWithEmailId(group.getGroupID(), user.getEmail()).getObject();
        assertEquals(1,userList.size());
    }

    @Test
    public void exitGroupTest() throws Exception {
        User user = new User("user1", "shashidhar", "shashi@gmail.com", "shashi", "9637355303", new HashSet<>());
        HashSet<User> userSet = new HashSet<>(Set.of(user));
        Group group = new Group("group1", "SPE Group", "user1", true, userSet);

        when(groupRepository.findById("group1")).thenReturn(Optional.of(group));
        when(userRepository.save(user)).thenReturn(user);

        UserGroupBody userGroupBody = new UserGroupBody("group1", "user1");
        assertEquals(true, groupService.exitGroup(userGroupBody).getSuccess());

        // lets check when user2 tries to exist from group1 which
        UserGroupBody userGroupBody1 = new UserGroupBody("group1", "user2");
        assertNotEquals(true, groupService.exitGroup(userGroupBody1).getSuccess());
    }

    @Test
    public void getAllUsersByGroupIDTest() {
        User user1 = new User("user1", "shashidhar", "shashi@gmail.com", "shashi", "9637355303", new HashSet<>());
        User user2 = new User("user2", "jayashree", "laxmi@gmail.com", "laxmi", "9876469390", new HashSet<>());
        HashSet<User> userSet = new HashSet<>(Set.of(user1, user2));

        // user1 and user2 are added in group
        Group group = new Group("group1", "SPE Group", "user1", true, userSet);

        when(groupRepository.findById("group1")).thenReturn(Optional.of(group));

        assertEquals(userSet.size(), groupService.getAllUsersByGroupID("group1").size());
    }

    @Test
    public void settleGroupTest() {
        Group group = new Group("group1", "SPE Group", "user1", false, new HashSet<>());
        when(groupRepository.findById("group1")).thenReturn(Optional.of(group));

        assertEquals(true, groupService.settleGroup("group1").getSuccess());
    }
}
