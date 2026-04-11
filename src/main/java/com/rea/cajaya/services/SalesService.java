package com.rea.cajaya.services;

import com.rea.cajaya.dtos.sale.CreateSaleRequest;
import com.rea.cajaya.dtos.sale.SaleTotalResponse;

public interface SalesService {
    SaleTotalResponse createSale(Long businessId, CreateSaleRequest request);
}
