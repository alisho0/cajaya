package com.rea.cajaya.services.impl;

import com.rea.cajaya.dtos.sale.CreateSaleLineRequest;
import com.rea.cajaya.dtos.sale.CreateSaleRequest;
import com.rea.cajaya.dtos.sale.SaleTotalResponse;
import com.rea.cajaya.models.Business;
import com.rea.cajaya.models.Product;
import com.rea.cajaya.models.Sale;
import com.rea.cajaya.models.SaleItem;
import com.rea.cajaya.repository.BusinessRepository;
import com.rea.cajaya.repository.ProductRepository;
import com.rea.cajaya.repository.SaleItemRepository;
import com.rea.cajaya.repository.SaleRepository;
import com.rea.cajaya.services.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private static final ZoneId ZONE = ZoneId.of("America/Argentina/Buenos_Aires");

    private final BusinessRepository businessRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;

    @Override
    @Transactional
    public SaleTotalResponse createSale(Long businessId, CreateSaleRequest request) {
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La venta debe incluir al menos un ítem.");
        }

        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el negocio."));

        Map<Long, Integer> mergedByProductId = new LinkedHashMap<>();
        for (CreateSaleLineRequest line : request.getItems()) {
            if (line.getProductId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cada ítem debe tener productId.");
            }
            if (line.getQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor a cero.");
            }
            mergedByProductId.merge(line.getProductId(), line.getQuantity(), Integer::sum);
        }

        List<ValidatedSaleLine> validated = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : mergedByProductId.entrySet()) {
            Long productId = entry.getKey();
            int qty = entry.getValue();

            Product product = productRepository.findByIdAndBusiness_IdForUpdate(productId, businessId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Producto no encontrado o no pertenece al negocio: " + productId
                    ));

            if (Boolean.FALSE.equals(product.getActive())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto está inactivo: " + productId);
            }
            if (product.getPrice() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El producto no tiene precio definido: " + productId);
            }
            if (product.getStock() < qty) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Stock insuficiente para el producto " + productId
                                + " (disponible: " + product.getStock() + ", solicitado: " + qty + ")."
                );
            }

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(qty));
            total = total.add(lineTotal);
            validated.add(new ValidatedSaleLine(product, qty));
        }

        Sale sale = new Sale();
        sale.setBusiness(business);
        sale.setUser(null);
        sale.setTotal(total);
        sale.setCreated_at(LocalDateTime.now(ZONE));
        sale = saleRepository.save(sale);

        for (ValidatedSaleLine line : validated) {
            Product product = line.product();
            int qty = line.quantity();

            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setProduct(product);
            item.setQuantity(qty);
            item.setPrice(product.getPrice());
            saleItemRepository.save(item);

            product.setStock(product.getStock() - qty);
            productRepository.save(product);
        }

        return SaleTotalResponse.builder().total(total).build();
    }

    private record ValidatedSaleLine(Product product, int quantity) {}
}
