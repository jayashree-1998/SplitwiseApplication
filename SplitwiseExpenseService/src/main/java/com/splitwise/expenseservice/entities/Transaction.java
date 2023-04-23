package com.splitwise.expenseservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String transactionID;

    @Column(nullable = false)
    private String groupID;

    @Column(nullable = false)
    private String PayerID;

    @Column(nullable = false)
    private String PayeeID;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private boolean hasPaid;
}
