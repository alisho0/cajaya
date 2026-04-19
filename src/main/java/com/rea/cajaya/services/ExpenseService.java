package com.rea.cajaya.services;

import com.rea.cajaya.dtos.expenses.CreateExpenseRequest;
import com.rea.cajaya.dtos.expenses.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    public ExpenseResponse registerExpense(Long businessId, CreateExpenseRequest request);
    public List<?> listExpenses();
}
