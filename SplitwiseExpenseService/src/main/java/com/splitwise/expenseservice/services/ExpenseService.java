package com.splitwise.expenseservice.services;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.payload.APIResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.payload.ExpenseDetail;

import java.util.Set;

public interface ExpenseService {
    APIResponse addExpense(ExpenseBody expenseBody);

    APIResponse getExpenseListWithGroupID(String groupID);

    APIResponse deleteExpense(String expenseID);

    APIResponse getExpenseDetailByExpenseID(String expenseID);
}
