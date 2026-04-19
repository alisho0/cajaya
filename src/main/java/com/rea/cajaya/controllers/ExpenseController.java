package com.rea.cajaya.controllers;

import com.rea.cajaya.dtos.expenses.CreateExpenseRequest;
import com.rea.cajaya.dtos.expenses.ExpenseResponse;
import com.rea.cajaya.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/business/{businessId}/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> createExpense(@PathVariable Long businessId, @RequestBody CreateExpenseRequest request) {
        ExpenseResponse response = expenseService.registerExpense(businessId, request);
        return ResponseEntity.ok(response);
    }
}
