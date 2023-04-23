package com.splitwise.userservice.repositories;

import com.splitwise.userservice.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    Group findGroupByOwnerID(String ownerID);
}
