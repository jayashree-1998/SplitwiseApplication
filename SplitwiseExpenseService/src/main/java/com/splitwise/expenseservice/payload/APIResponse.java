package com.splitwise.expenseservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {

    private Object object;
    private boolean success;

    public boolean getSuccess() {
        return success;
    }
}
