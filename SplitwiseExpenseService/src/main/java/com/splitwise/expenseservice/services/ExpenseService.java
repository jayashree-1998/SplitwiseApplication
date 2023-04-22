package com.splitwise.expenseservice.services;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.payload.ApiResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;

import java.util.Set;

public interface ExpenseService {
    ApiResponse addExpense(ExpenseBody expenseBody);

    Set<Expense> getExpenseListWithGroupID(String groupID);

    ApiResponse deleteExpense(String expenseID);
}
