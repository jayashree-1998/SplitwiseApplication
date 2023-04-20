package com.splitwise.userservice.repositories;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
}
