package com.splitwise.expenseservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettleUpItem {
    private String giverID;
    private String receiverID;
    private double amount;
}
