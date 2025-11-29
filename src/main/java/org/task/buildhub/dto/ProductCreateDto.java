package org.task.buildhub.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreateDto {
    private String sku;
    private String name;
    private String description;
    private String barcode;
    private String brand;
    private Long unitId;
    private BigDecimal retailPrice;
    private BigDecimal wholesalePrice;
    private Integer minStockLevel;
}