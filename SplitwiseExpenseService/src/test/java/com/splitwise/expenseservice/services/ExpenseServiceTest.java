package com.splitwise.expenseservice.services;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.entities.Owe;
import com.splitwise.expenseservice.entities.Paid;
import com.splitwise.expenseservice.entities.Transaction;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.payload.ExpenseDetail;
import com.splitwise.expenseservice.payload.UserAmount;
import com.splitwise.expenseservice.respository.ExpenseRepository;
import com.splitwise.expenseservice.respository.OweRepository;
import com.splitwise.expenseservice.respository.PaidRepository;
import com.splitwise.expenseservice.respository.TransactionRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ExpenseServiceTest {
    @Autowired
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void getExpenseListWithGroupIDTest() {
        Expense expense1 = new Expense("expense1","food", "group1", 100.0, new Date(), "user1", false, new HashSet<>(), new HashSet<>());
        Expense expense2 = new Expense("expense2","cloth", "group2", 200.0, new Date(), "user1", false, new HashSet<>(), new HashSet<>());

        Set<Expense> expenses = Set.of(expense1, expense2);

        when(expenseRepository.findExpenseByGroupID("group1")).thenReturn(expenses);
        assertEquals(2, expenseService.getExpenseListWithGroupID("group1").size());
    }

    @Test
    public void deleteExpenseTest() {
        Expense expense = new Expense("expense1","food", "group1", 100.0, new Date(), "user1", false, new HashSet<>(), new HashSet<>());

        when(expenseRepository.findById("expense1")).thenReturn(Optional.of(expense));

        assertNotEquals(true, expenseService.deleteExpense("expense2").getSuccess());
        assertEquals(true, expenseService.deleteExpense("expense1").getSuccess());
    }

    @Test
    public void deleteExpenseWithGroupIDTest() {
        Expense expense1 = new Expense("expense1","food", "group1", 100.0, new Date(), "user1", false, new HashSet<>(), new HashSet<>());
        Expense expense2 = new Expense("expense2","cloth", "group1", 200.0, new Date(), "user2", false, new HashSet<>(), new HashSet<>());

        Set<Expense> expenses = Set.of(expense1, expense2);
        when(expenseRepository.findExpenseByGroupID("group1")).thenReturn(expenses);
        assertEquals(true, expenseService.deleteExpenseWithGroupID("group1").getSuccess());
    }

    @Test
    public void getExpenseDetailsByExpenseIDTest() {
        Expense expense = new Expense("expense1","food", "group1", 100.0, new Date(), "user1", false, new HashSet<>(), new HashSet<>());

        ExpenseDetail expenseDetail = new ExpenseDetail();
        expenseDetail.setExpenseID(expense.getExpenseID());
        expenseDetail.setDate(expense.getDate());
        expenseDetail.setAmount(expense.getAmount());
        expenseDetail.setPaidSet(expense.getPaidSet());
        expenseDetail.setOweSet(expense.getOweSet());
        expenseDetail.setGroupID(expense.getGroupID());
        expenseDetail.setAddedBy(expense.getAddedBy());

        when(expenseRepository.findById("expense1")).thenReturn(Optional.of(expense));

        ExpenseDetail actualExpenseDetail = (ExpenseDetail) expenseService.getExpenseDetailByExpenseID("expense1").getObject();

        assertEquals(expense.getExpenseID(),actualExpenseDetail.getExpenseID());
        assertEquals(expense.getAmount(),actualExpenseDetail.getAmount());
    }

    @Test
    public void showTransactionForGroupTest() {
        Transaction transaction1 = new Transaction("t1", "g1", "u1", "u2", 100.0, false, false);
        Transaction transaction2 = new Transaction("t2", "g1", "u1", "u3", 150.0, false, false);
        Set<Transaction> transactionSet = Set.of(transaction1, transaction2);
        when(transactionRepository.findTransactionByGroupID("g1")).thenReturn(transactionSet);
        Set<Transaction> actualTransactionSet = (Set<Transaction>) expenseService.showTransactionForGroup("g1").getObject();
        assertEquals(2, actualTransactionSet.size());
    }

}
