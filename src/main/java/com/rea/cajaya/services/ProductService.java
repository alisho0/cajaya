package com.rea.cajaya.services;

import com.rea.cajaya.dtos.product.CreateProductRequest;
import com.rea.cajaya.dtos.product.ProductResponse;
import com.rea.cajaya.dtos.product.UpdateProductRequest;
import com.rea.cajaya.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request, Long businessId);

    List<ProductResponse> getProducts(Long businessId);

    ProductResponse updateProduct(UpdateProductRequest request, Long productId);

    Optional<Product> findByBarcode(String barcode, Long businessId);
}
