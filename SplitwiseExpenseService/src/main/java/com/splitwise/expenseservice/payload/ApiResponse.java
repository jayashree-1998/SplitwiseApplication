package com.splitwise.expenseservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean success;
}
