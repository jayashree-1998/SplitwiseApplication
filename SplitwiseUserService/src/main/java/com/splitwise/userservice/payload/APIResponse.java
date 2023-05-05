package com.splitwise.userservice.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {
    private Object object;
    private boolean success;

    public boolean getSuccess() {
        return this.success;
    }
}
