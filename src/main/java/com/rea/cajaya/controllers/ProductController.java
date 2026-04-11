package com.rea.cajaya.controllers;

import com.rea.cajaya.dtos.product.CreateProductRequest;
import com.rea.cajaya.dtos.product.ProductResponse;
import com.rea.cajaya.dtos.product.UpdateProductRequest;
import com.rea.cajaya.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business/{businessId}/catalog/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest request, @PathVariable Long businessId) {
        ProductResponse product = productService.createProduct(request, businessId);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@PathVariable Long businessId){
        List<ProductResponse> productResponses = productService.getProducts(businessId);
        return ResponseEntity.ok(productResponses);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> putProduct(@RequestBody UpdateProductRequest request, @PathVariable Long productId) {
        ProductResponse productResponse = productService.updateProduct(request, productId);
        return ResponseEntity.ok(productResponse);
    }
}
