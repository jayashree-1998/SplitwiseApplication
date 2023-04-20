package com.splitwise.userservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userID;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // TODO: Make mobilenumber field unique
    @Column(name = "mobilenumber",length = 14,nullable = false)
    private String mobileNumber;

    @JsonIgnore
    @ManyToMany(mappedBy = "userList", fetch = FetchType.EAGER)
    private Set<Group> groupList = new HashSet<>();

}