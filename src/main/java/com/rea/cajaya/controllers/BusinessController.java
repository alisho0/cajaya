package com.rea.cajaya.controllers;

import com.rea.cajaya.dtos.business.CreateBusinessRequest;
import com.rea.cajaya.models.Business;
import com.rea.cajaya.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping
    public ResponseEntity<Business> createBusiness(@RequestBody CreateBusinessRequest request) {
        System.out.println(request);
        Business business = businessService.createBusiness(request);
        return ResponseEntity.ok(business);
    }
}
