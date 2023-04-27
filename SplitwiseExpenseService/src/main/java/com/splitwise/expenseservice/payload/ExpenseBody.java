package com.splitwise.expenseservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseBody {

    private String groupID;
    private double amount;
    private String addedBy;

    private String expenseName;
    private Set<UserAmount> paidBySet;
    private Set<UserAmount> owedBySet;
}
