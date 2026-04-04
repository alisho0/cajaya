package com.rea.cajaya.dtos.product;

import com.rea.cajaya.models.Business;
import com.rea.cajaya.models.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ProductResponse {
    private Long id;
    private Long business_id;

    private String name;
    private BigDecimal price;
    private int stock;
    private int min_stock;
    private String barcode;
    private String category;
    private Boolean active;
    private LocalDateTime created_at;

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .business_id(product.getBusiness().getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .min_stock(product.getMin_stock())
                .barcode(product.getBarcode())
                .category(product.getCategory())
                .active(product.getActive())
                .created_at(product.getCreated_at())
                .build();
    }
}
