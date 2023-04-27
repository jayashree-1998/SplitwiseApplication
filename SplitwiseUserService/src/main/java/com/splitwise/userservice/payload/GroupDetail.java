package com.splitwise.userservice.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetail {
    private Group group;
    private Set<User> userList;
    private Set<Expense> expenseList;
}
