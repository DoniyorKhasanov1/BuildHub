package org.task.buildhub.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockUpdateDto {
    private Integer quantity;
    private BigDecimal costPrice;
}