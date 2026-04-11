package com.rea.cajaya.controllers;

import com.rea.cajaya.dtos.sale.CreateSaleRequest;
import com.rea.cajaya.dtos.sale.SaleTotalResponse;
import com.rea.cajaya.services.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/business/{businessId}/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping
    public ResponseEntity<SaleTotalResponse> createSale(
            @PathVariable Long businessId,
            @RequestBody CreateSaleRequest request
    ) {
        SaleTotalResponse response = salesService.createSale(businessId, request);
        return ResponseEntity.ok(response);
    }
}
