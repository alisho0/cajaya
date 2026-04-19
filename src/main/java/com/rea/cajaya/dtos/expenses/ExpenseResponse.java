package com.rea.cajaya.dtos.expenses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String category;
    private String note;
    private BigDecimal amount;
    private LocalDateTime created_at;
}
