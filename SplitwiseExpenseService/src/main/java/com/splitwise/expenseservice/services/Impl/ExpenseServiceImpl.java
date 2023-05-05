package com.splitwise.expenseservice.services.Impl;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.entities.Owe;
import com.splitwise.expenseservice.entities.Paid;
import com.splitwise.expenseservice.exceptions.ResourceNotFound;
import com.splitwise.expenseservice.payload.*;
import com.splitwise.expenseservice.respository.ExpenseRepository;
import com.splitwise.expenseservice.respository.OweRepository;
import com.splitwise.expenseservice.respository.PaidRepository;
import com.splitwise.expenseservice.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {


    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    PaidRepository paidRepository;

    @Autowired
    OweRepository oweRepository;
    @Override
    public APIResponse addExpense(ExpenseBody expenseBody) {
        try {
            Date date = new Date();
            System.out.println(date);
            Expense expense = new Expense();
            expense.setGroupID(expenseBody.getGroupID());
            expense.setAmount(expenseBody.getAmount());
            expense.setAddedBy(expenseBody.getAddedBy());
            expense.setExpenseName(expenseBody.getExpenseName());
            expense.setDate(date);
            Expense savedExpense = this.expenseRepository.save(expense);

            Set<UserAmount> paidBySet = expenseBody.getPaidBySet();
            for(UserAmount user: paidBySet) {
                Paid paid = new Paid();
                paid.setAmount(user.getAmount());
                paid.setUserID(user.getUserID());
                paid.setExpense(savedExpense);
                this.paidRepository.save(paid);
            }

            Set<UserAmount> owedBySet = expenseBody.getOwedBySet();
            for(UserAmount user: owedBySet) {
                Owe owe = new Owe();
                owe.setAmount(user.getAmount());
                owe.setUserID(user.getUserID());
                owe.setExpense(savedExpense);
                this.oweRepository.save(owe);
            }
            return new APIResponse("Expense added successfully", true);
        } catch (Exception e) {
            return new APIResponse("Error in adding expense", false);
        }
    }

    @Override
    public Set<Expense> getExpenseListWithGroupID(String groupID) {
        HashSet<Expense> expenses = new HashSet<>();
        Set<Expense> expenseSet = this.expenseRepository.findExpenseByGroupID(groupID);
        if(expenseSet != null) {
            expenses = (HashSet<Expense>) expenseSet;
            return expenses;
        }
        else {
            return expenses;
        }
    }

    @Override
    public APIResponse deleteExpense(String expenseID) {
        try {
            this.expenseRepository.deleteById(expenseID);
            return new APIResponse("Successfully deleted expense!", true);
        } catch (Exception e) {
            return new APIResponse("Error deleting expense!", false);
        }
    }

    @Override
    public APIResponse deleteExpenseWithGroupID(String groupID) {
        try {
            Set<Expense> expenses = this.expenseRepository.findExpenseByGroupID(groupID);
            for(Expense e: expenses) {
                this.expenseRepository.deleteById(e.getExpenseID());
            }
            return new APIResponse("Expenses deleted!", true);
        } catch(Exception e) {
            return new APIResponse("Error deleting expenses!", false);
        }
    }

    @Override
    public APIResponse getExpenseDetailByExpenseID(String expenseID) {

        Expense expense = this.expenseRepository.findById(expenseID).orElseThrow(()-> new ResourceNotFound("Expense", "ID"));
        ExpenseDetail expenseDetail = new ExpenseDetail();
        expenseDetail.setExpenseID(expense.getExpenseID());
        expenseDetail.setDate(expense.getDate());
        expenseDetail.setAmount(expense.getAmount());
        expenseDetail.setPaidSet(expense.getPaidSet());
        expenseDetail.setOweSet(expense.getOweSet());
        expenseDetail.setGroupID(expense.getGroupID());
        expenseDetail.setAddedBy(expense.getAddedBy());
        return new APIResponse(expenseDetail,true);
    }

    @Override
    public void settleUpGroup(String groupID) {

//        // get expense list from expense_table
//        Set<Expense> expenseSet = this.expenseRepository.findExpenseByGroupID(groupID);
//
//
//        // Settle Up Algorithm
//
//
//        // balance list
//        HashMap<String, Double> balanceList = new HashMap<>();
//        // TODO: create balance list
//
//        for(Expense expense: expenseSet) {
//            Set<Paid> paidSet = expense.getPaidSet();
//            Set<Owe> oweSet = expense.getOweSet();
//
//            HashMap<String, Double> paidHashMap = new HashMap<>();
//            HashMap<String, Double> oweHashMap = new HashMap<>();
//
//            for(Paid paid: paidSet) {
//                paidHashMap.put(paid.getUserID(), paid.getAmount());
//            }
//            for(Owe owe: oweSet) {
//                oweHashMap.put(owe.getUserID(), owe.getAmount());
//            }
//
//            double temp = 0;
//            for(Map.Entry<String, Paid> paidEntry:  paidHashMap.entrySet()) {
//
//            }
//
//        }
//
//
//
//
//        // giver, receiver list
//        HashMap<String, Double> giverList = new HashMap<String, Double>();
//        HashMap<String, Double> receiverList = new HashMap<String, Double>();
//
//        // settle up List
//        ArrayList<SettleUpItem> settleUpItems = new ArrayList<>();
//
//
    }
}
