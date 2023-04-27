package com.splitwise.expenseservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private double amount;

    @Column(nullable = false)
    private String userID;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "expenseid")
    private Expense expense;
}
