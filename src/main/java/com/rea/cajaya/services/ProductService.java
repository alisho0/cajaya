package com.rea.cajaya.services;

import com.rea.cajaya.dtos.product.CreateProductRequest;
import com.rea.cajaya.dtos.product.UpdateProductRequest;
import com.rea.cajaya.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(CreateProductRequest request, Long businessId);

    List<Product> getProducts(Long businessId);

    Product updateProduct(Long productId, UpdateProductRequest request);

    Optional<Product> findByBarcode(String barcode, Long businessId);
}
