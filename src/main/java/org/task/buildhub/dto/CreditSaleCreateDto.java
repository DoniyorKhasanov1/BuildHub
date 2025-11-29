package org.task.buildhub.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreditSaleCreateDto {
    private Long saleId;
    private LocalDate dueDate;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
}