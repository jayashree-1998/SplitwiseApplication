package com.splitwise.userservice.payload;

import com.splitwise.userservice.entities.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

        private String user_id;
        private String name;
        private String email;
        private String mobileNumber;
        private Set<Group> groupList;
        

}
