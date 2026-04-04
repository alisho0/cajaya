package com.rea.cajaya.services.impl;

import com.rea.cajaya.dtos.product.CreateProductRequest;
import com.rea.cajaya.dtos.product.ProductResponse;
import com.rea.cajaya.dtos.product.UpdateProductRequest;
import com.rea.cajaya.models.Business;
import com.rea.cajaya.models.Product;
import com.rea.cajaya.repository.BusinessRepository;
import com.rea.cajaya.repository.ProductRepository;
import com.rea.cajaya.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BusinessRepository businessRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request, Long businessId) {
        Product product = new Product();
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("No se encontró el negocio enviado."));

        product.setBusiness(business);
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setMin_stock(request.getMin_stock());
        product.setBarcode(request.getBarcode());
        product.setCategory(request.getCategory());
        product.setActive(request.getActive());
        product.setCreated_at(
                LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"))
        );

        Product p = productRepository.save(product);

        return ProductResponse.fromEntity(p);
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
