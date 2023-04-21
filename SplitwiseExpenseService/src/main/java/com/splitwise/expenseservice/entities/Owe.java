package com.splitwise.expenseservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "owe_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Owe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String oweID;

    @Column(nullable = false)
    private String owedBy;

    @Column(nullable = false)
    private double amount;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "expenseid")
    private Expense expense;

}
