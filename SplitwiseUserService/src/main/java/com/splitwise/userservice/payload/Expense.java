package com.splitwise.userservice.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Expense {
    private String expenseID;
    private String expenseName;
    private String groupID;
    private double amount;
    private Date date;
    private String addedBy;

    private Set<Paid> paidSet = new HashSet<>();
    private Set<Owe> oweSet = new HashSet<>();

}
