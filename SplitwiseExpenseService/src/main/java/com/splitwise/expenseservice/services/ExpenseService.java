package com.splitwise.expenseservice.services;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.payload.APIResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.payload.ExpenseDetail;

import java.util.Set;

public interface ExpenseService {
    APIResponse addExpense(ExpenseBody expenseBody);

    Set<ExpenseDetail> getExpenseListWithGroupID(String groupID);

    APIResponse deleteExpense(String expenseID);

    APIResponse deleteExpenseWithGroupID(String groupID);

    APIResponse getExpenseDetailByExpenseID(String expenseID);

    APIResponse settleUpGroup(String groupID);

    APIResponse showTransactionForGroup(String groupID);
}
