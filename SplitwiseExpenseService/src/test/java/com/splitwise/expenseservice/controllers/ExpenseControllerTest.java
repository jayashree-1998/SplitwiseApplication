package com.splitwise.expenseservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.splitwise.expenseservice.controller.ExpenseController;
import com.splitwise.expenseservice.payload.APIResponse;
import com.splitwise.expenseservice.payload.ExpenseBody;
import com.splitwise.expenseservice.payload.ExpenseDetail;
import com.splitwise.expenseservice.services.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ExpenseController.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExpenseService expenseService;

    @Test
    public void addExpenseAPITest() throws Exception {
        ExpenseBody expenseBody = new ExpenseBody("group1", 100.0, "user1", "food", new HashSet<>(), new HashSet<>());
        APIResponse apiResponse = new APIResponse("this is test object", true);
        when(expenseService.addExpense(expenseBody)).thenReturn(apiResponse);

        this.mockMvc.perform(post("/expense/add-expense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseBody)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void getExpenseListForGroupAPITest() throws Exception {
        Set<ExpenseDetail> expenseDetailSet = Set.of(new ExpenseDetail("expense1", "food", "group1", 100.0, new Date(),"user1", new HashSet<>(), new HashSet<>(), false), new ExpenseDetail("expense2", "food", "group2", 200.0, new Date(),"user2", new HashSet<>(), new HashSet<>(), false));

        when(expenseService.getExpenseListWithGroupID("group1")).thenReturn(expenseDetailSet);

        this.mockMvc.perform(get("/expense/get-expense-list-by-group-id/{groupID}", "group1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    public void getExpenseDetailAPITest() throws Exception{
        APIResponse apiResponse = new APIResponse("this is test object", true);
        when(expenseService.getExpenseDetailByExpenseID("expense1")).thenReturn(apiResponse);

        this.mockMvc.perform(get("/expense/get-expense-detail/{expenseID}", "expense1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void deleteExpenseAPITest() throws Exception{
        APIResponse apiResponse = new APIResponse("this is test object", true);
        when(expenseService.deleteExpense("expense1")).thenReturn(apiResponse);

        this.mockMvc.perform(delete("/expense/delete-expense/{expenseID}", "expense1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void deleteExpenseWithGroupAPITest() throws Exception{
        APIResponse apiResponse = new APIResponse("this is test object", true);
        when(expenseService.deleteExpenseWithGroupID("group1")).thenReturn(apiResponse);

        this.mockMvc.perform(delete("/expense/delete-expense-of-group/{groupID}", "group1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void settleUpAPITest() throws Exception{
        APIResponse apiResponse = new APIResponse("this is test object", true);
        when(expenseService.settleUpGroup("group1")).thenReturn(apiResponse);

        this.mockMvc.perform(get("/expense/settle-up/{groupID}", "group1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void showTransactionAPITest() throws Exception{
        APIResponse apiResponse = new APIResponse("this is test object", true);
        when(expenseService.showTransactionForGroup("group1")).thenReturn(apiResponse);

        this.mockMvc.perform(get("/expense/show-transaction/{groupID}", "group1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

}
