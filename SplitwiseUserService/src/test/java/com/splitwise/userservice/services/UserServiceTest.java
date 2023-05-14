package com.splitwise.userservice.services;


import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import com.splitwise.userservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository repository;

    @Test
    public void registerTest() {
        User user = new User("user1", "shashidhar", "shashi@gmail.com", "shashi", "9637355303", new HashSet<>());
        when(repository.save(user)).thenReturn(user);
        assertEquals(true, userService.registerUser(user).getSuccess());
    }

    @Test
    public void loginTest() {
        User user = new User("user1", "shashidhar", "shashi@gmail.com", "shashi", "9637355303", new HashSet<>());
        when(repository.findUserByEmailAndPassword("shashi@gmail.com", "shashi")).thenReturn(user);
        assertEquals("shashidhar", userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword()).getName());
    }

    @Test
    public void getAllGroupsByUserIDTest() {
        Group group1 = new Group("group1", "SPE Group", "user1", false, new HashSet<>());
        Group group2 = new Group("group2", "VR Group", "user2", false, new HashSet<>());

        HashSet<Group> groupHashSet = new HashSet<>(Set.of(group1, group2));

        User user = new User("user1", "shashidhar", "shashi@gmail.com", "shashi", "9637355303", groupHashSet);

        when(repository.findById(user.getUserID())).thenReturn(Optional.of(user));
        assertEquals(2, userService.getAllGroupsByUserID(user.getUserID()).size());
    }
}
