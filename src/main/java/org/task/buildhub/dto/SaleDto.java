package org.task.buildhub.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleDto {
    private Long id;
    private String receiptNumber;
    private Long cashierId;
    private String cashierName;
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private BigDecimal subTotal;
    private String paymentMethod;
    private String customerName;
    private String customerPhone;
    private LocalDateTime saleDate;
    private List<SaleItemDto> saleItems;
}
