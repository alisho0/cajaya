package com.rea.cajaya.dtos.expenses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateExpenseRequest {
    private String category;
    private String note;
    private BigDecimal amount;
}
