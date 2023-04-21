package com.splitwise.expenseservice.respository;

import com.splitwise.expenseservice.entities.Owe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OweRepository extends JpaRepository<Owe, String> {
}
