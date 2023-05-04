package com.splitwise.expenseservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class APIResponse {

    private Object object;
    private boolean success;
}
