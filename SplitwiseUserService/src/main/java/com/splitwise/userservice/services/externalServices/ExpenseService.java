package com.splitwise.userservice.services.externalServices;

import com.splitwise.userservice.payload.APIResponse;
import com.splitwise.userservice.payload.Expense;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "EXPENSE-SERVICE")
public interface ExpenseService {

    @GetMapping("/expense/get-expense-list-by-group-id/{groupID}")
    Set<Expense> getExpenses(@PathVariable("groupID") String groupID);

    @DeleteMapping("/expense/delete-expense-of-group/{groupID}")
    APIResponse deleteExpensesOfGroup(@PathVariable("groupID") String groupID);
}
