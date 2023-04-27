package com.splitwise.expenseservice.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paid_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Paid {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paidID;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String userID;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "expenseid")
    private Expense expense;
}
