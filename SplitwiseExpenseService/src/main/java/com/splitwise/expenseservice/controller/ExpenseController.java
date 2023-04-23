package com.splitwise.expenseservice.controller;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.payload.ApiResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/add-expense")
    public ResponseEntity<ApiResponse> addExpense(@RequestBody ExpenseBody expenseBody) {
        ApiResponse apiResponse = this.expenseService.addExpense(expenseBody);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-expense-list-by-group-id/{groupID}")
    public ResponseEntity<Set<Expense>> getExpenseListForGroup(@PathVariable String groupID) {
        Set<Expense> expenseSet = this.expenseService.getExpenseListWithGroupID(groupID);
        return new ResponseEntity<>(expenseSet, HttpStatus.OK);
    }

    @DeleteMapping ("/delete-expense/{expenseID}")
    public ResponseEntity<ApiResponse> deleteExpense(@PathVariable String expenseID) {
        ApiResponse apiResponse = this.expenseService.deleteExpense(expenseID);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/settle-up/{groupID}")
    public ResponseEntity<ApiResponse> settleUp(@PathVariable String groupID) {
        // TODO: transaction algorithm not done
        return new ResponseEntity<>(new ApiResponse("", false), HttpStatus.OK);
    }
}
