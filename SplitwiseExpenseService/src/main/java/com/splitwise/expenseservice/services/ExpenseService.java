package com.splitwise.expenseservice.services;

import com.splitwise.expenseservice.payload.ApiResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;

public interface ExpenseService {
    ApiResponse addExpense(ExpenseBody expenseBody);
}
