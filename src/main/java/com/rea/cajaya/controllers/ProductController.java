package com.rea.cajaya.controllers;

import com.rea.cajaya.dtos.product.CreateProductRequest;
import com.rea.cajaya.models.Product;
import com.rea.cajaya.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business/{businessId}/catalog/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request, @PathVariable Long businessId) {
        Product product = productService.createProduct(request, businessId);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(){
        return ResponseEntity.ok("");
    }
}
