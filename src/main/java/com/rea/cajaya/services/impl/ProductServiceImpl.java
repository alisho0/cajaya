package com.rea.cajaya.services.impl;

import com.rea.cajaya.dtos.product.CreateProductRequest;
import com.rea.cajaya.dtos.product.UpdateProductRequest;
import com.rea.cajaya.models.Product;
import com.rea.cajaya.repository.ProductRepository;
import com.rea.cajaya.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Override
    public Product createProduct(CreateProductRequest request, Long businessId) {
        return null;
    }

    @Override
    public List<Product> getProducts(Long businessId) {
        return List.of();
    }

    @Override
    public Product updateProduct(Long productId, UpdateProductRequest request) {
        return null;
    }

    @Override
    public Optional<Product> findByBarcode(String barcode, Long businessId) {
        return Optional.empty();
    }
}
