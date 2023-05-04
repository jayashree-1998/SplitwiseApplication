package com.splitwise.expenseservice.controller;

import com.splitwise.expenseservice.entities.Expense;
import com.splitwise.expenseservice.payload.APIResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.payload.ExpenseDetail;
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
    public ResponseEntity<APIResponse> addExpense(@RequestBody ExpenseBody expenseBody) {
        APIResponse apiResponse = this.expenseService.addExpense(expenseBody);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get-expense-list-by-group-id/{groupID}")
    public ResponseEntity<APIResponse> getExpenseListForGroup(@PathVariable String groupID) {
        APIResponse apiResponse = this.expenseService.getExpenseListWithGroupID(groupID);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("get-expense-detail/{expenseID}")
    public ResponseEntity<APIResponse> getExpenseDetail(@PathVariable String expenseID) {
        APIResponse apiResponse = this.expenseService.getExpenseDetailByExpenseID(expenseID);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping ("/delete-expense/{expenseID}")
    public ResponseEntity<APIResponse> deleteExpense(@PathVariable String expenseID) {
        APIResponse apiResponse = this.expenseService.deleteExpense(expenseID);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/settle-up/{groupID}")
    public ResponseEntity<APIResponse> settleUp(@PathVariable String groupID) {
        // TODO: transaction algorithm not done
        return new ResponseEntity<>(new APIResponse("", false), HttpStatus.OK);
    }


}
