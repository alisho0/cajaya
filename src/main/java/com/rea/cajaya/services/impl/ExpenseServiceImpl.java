package com.rea.cajaya.services.impl;

import com.rea.cajaya.dtos.expenses.CreateExpenseRequest;
import com.rea.cajaya.dtos.expenses.ExpenseResponse;
import com.rea.cajaya.models.Business;
import com.rea.cajaya.models.Expense;
import com.rea.cajaya.repository.BusinessRepository;
import com.rea.cajaya.repository.ExpenseRepository;
import com.rea.cajaya.services.ExpenseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final BusinessRepository businessRepository;

    @Override
    @Transactional
    public ExpenseResponse registerExpense(Long businessId, CreateExpenseRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el negocio."));

        if (request.getCategory() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoría no debe ser vacía.");
        }
        if (request.getAmount() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto no debe estar vacío.");
        }
        Expense expense = new Expense();
        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());
        expense.setBusiness(business);
        expense.setNote(request.getNote());
        expense.setCreated_at(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));

        expenseRepository.save(expense);

        return ExpenseResponse.builder()
                .id(expense.getId())
                .note(expense.getNote())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .created_at(expense.getCreated_at())
                .build();
    }

    @Override
    public List<?> listExpenses() {
        return List.of();
    }
}
