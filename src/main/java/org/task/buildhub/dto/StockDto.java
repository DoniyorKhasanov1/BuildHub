package org.task.buildhub.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class StockDto {
    private Long id;
    private Long productId;
    private String productName;
    private String productSku;
    private Long warehouseId;
    private String warehouseName;
    private Integer quantity;
    private BigDecimal costPrice;
    private BigDecimal totalValue;
}
