package com.splitwise.expenseservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "expense_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String expenseID;

    @Column(nullable = false)
    private String expenseName;

    @Column(nullable = false)
    private String groupID;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String addedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Paid> paidSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "expense", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Owe> oweSet = new HashSet<>();

}
