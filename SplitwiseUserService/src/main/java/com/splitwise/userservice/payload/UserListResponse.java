package com.splitwise.userservice.payload;

import com.splitwise.userservice.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {

    String status;
    Set<User> users = new HashSet<>();
}
