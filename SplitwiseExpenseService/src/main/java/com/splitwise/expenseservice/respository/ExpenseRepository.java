package com.splitwise.expenseservice.respository;

import com.splitwise.expenseservice.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
//    Set<Expense> findExpenseByGroupIDAndSettled(String groupID, boolean isSettled);
    Set<Expense> findExpenseByGroupID(String groupID);

    @Query(value = "select * from expense_table e where e.groupid = :groupID and e.is_settled=false", nativeQuery = true)
    Set<Expense> findExpenseByGroupIDAndIsNotSettled(String groupID);
}
