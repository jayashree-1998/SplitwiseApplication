package com.splitwise.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_table")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String groupID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ownerID;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name ="user_group_table", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userList = new HashSet<>();
}
