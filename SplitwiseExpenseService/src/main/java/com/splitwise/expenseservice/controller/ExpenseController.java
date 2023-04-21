package com.splitwise.expenseservice.controller;

import com.splitwise.expenseservice.payload.ApiResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
