package com.splitwise.expenseservice.services.externalServices;


import com.splitwise.expenseservice.payload.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "USER-SERVICE")
public interface UserService {

    @PostMapping("/group/make-group-settle/{groupID}")
    APIResponse settleGroup(@PathVariable String groupID);
}
