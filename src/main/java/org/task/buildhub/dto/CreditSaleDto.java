package org.task.buildhub.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreditSaleDto {
    private Long id;
    private Long saleId;
    private String receiptNumber;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private LocalDate dueDate;
    private String status;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
}