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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        if (productRepository.existsByBarcode(request.getBarcode())) {
            throw new RuntimeException("La barra de código ingresada ya existe");
        }
        product.setBarcode(request.getBarcode());
        product.setCategory(request.getCategory());
        product.setActive(request.getActive());
        product.setName(request.getName());
        product.setCreated_at(
                LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"))
        );

        Product p = productRepository.save(product);

        return ProductResponse.fromEntity(p);
    }

    @Override
    public List<ProductResponse> getProducts(Long businessId) {
        List<Product> productList = productRepository.findByBusiness_Id(businessId);

        return productList.stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UpdateProductRequest request, Long productId) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud no puede ser nulo.");
        }
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el producto enviado."));

        String name = request.getName();
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio.");
        }
        if (request.getPrice() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio es obligatorio.");
        }
        String barcode = request.getBarcode();
        if (barcode == null || barcode.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El código de barras es obligatorio.");
        }
        String category = request.getCategory();
        if (category == null || category.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoría es obligatoria.");
        }
        if (request.getActive() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado activo es obligatorio.");
        }

        if (productRepository.existsByBarcodeAndIdNot(barcode, productId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La barra de código ingresada ya existe");
        }

        p.setName(name);
        p.setPrice(request.getPrice());
        p.setStock(request.getStock());
        p.setMin_stock(request.getMin_stock());
        p.setBarcode(barcode);
        p.setCategory(category);
        p.setActive(request.getActive());

        try {
            Product saved = productRepository.save(p);
            return ProductResponse.fromEntity(saved);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se pudo actualizar el producto por un conflicto de datos en la base.",
                    e
            );
        }
    }

    @Override
    public Optional<Product> findByBarcode(String barcode, Long businessId) {
        return Optional.empty();
    }
}
