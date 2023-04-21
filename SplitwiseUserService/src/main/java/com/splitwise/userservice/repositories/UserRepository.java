package com.splitwise.userservice.repositories;

import com.splitwise.userservice.entities.Group;
import com.splitwise.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    //custom query
    User findUserByEmailAndPassword(String email,String password);

    User findUserByEmail(String email);
}
