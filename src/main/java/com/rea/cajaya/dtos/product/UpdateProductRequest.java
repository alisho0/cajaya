package com.rea.cajaya.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Builder
@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequest {
        private String name;
        private BigDecimal price;
        private int stock;
        private int min_stock;
        private String barcode;
        private String category;
        private Boolean active;
}
