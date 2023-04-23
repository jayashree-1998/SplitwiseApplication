package com.splitwise.expenseservice.respository;

import com.splitwise.expenseservice.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    Set<Expense> findExpenseByGroupID(String groupID);
}
