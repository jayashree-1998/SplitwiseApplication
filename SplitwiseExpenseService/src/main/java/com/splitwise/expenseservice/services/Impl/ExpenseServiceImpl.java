package com.splitwise.expenseservice.services.Impl;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.entities.Owe;
import com.splitwise.expenseservice.entities.Paid;
import com.splitwise.expenseservice.payload.ApiResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.payload.UserAmount;
import com.splitwise.expenseservice.respository.ExpenseRepository;
import com.splitwise.expenseservice.respository.OweRepository;
import com.splitwise.expenseservice.respository.PaidRepository;
import com.splitwise.expenseservice.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class ExpenseServiceImpl implements ExpenseService {


    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    PaidRepository paidRepository;

    @Autowired
    OweRepository oweRepository;
    @Override
    public ApiResponse addExpense(ExpenseBody expenseBody) {
        try {
            Date date = new Date();
            System.out.println(date);
            Expense expense = new Expense();
            expense.setGroupID(expenseBody.getGroupID());
            expense.setAmount(expenseBody.getAmount());
            expense.setAddedBy(expenseBody.getAddedBy());
            expense.setDate(date);
            Expense savedExpense = this.expenseRepository.save(expense);

            Set<UserAmount> paidBySet = expenseBody.getPaidBySet();
            for(UserAmount user: paidBySet) {
                Paid paid = new Paid();
                paid.setAmount(user.getAmount());
                paid.setPaidBy(user.getUserID());
                paid.setExpense(savedExpense);
                this.paidRepository.save(paid);
            }

            Set<UserAmount> owedBySet = expenseBody.getOwedBySet();
            for(UserAmount user: owedBySet) {
                Owe owe = new Owe();
                owe.setAmount(user.getAmount());
                owe.setOwedBy(user.getUserID());
                owe.setExpense(savedExpense);
                this.oweRepository.save(owe);
            }
            return new ApiResponse("expense added successfully", true);
        } catch (Exception e) {
            return new ApiResponse("error in adding expense", false);
        }

    }
}
