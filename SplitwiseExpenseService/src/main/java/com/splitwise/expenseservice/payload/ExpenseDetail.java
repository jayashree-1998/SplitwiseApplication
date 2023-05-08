package com.splitwise.expenseservice.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splitwise.expenseservice.entities.Owe;
import com.splitwise.expenseservice.entities.Paid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDetail {
    private String expenseID;

    private String expenseName;
    private String groupID;
    private double amount;
    private Date date;
    private String addedBy;
    private Set<Paid> paidSet = new HashSet<>();
    private Set<Owe> oweSet = new HashSet<>();
}
