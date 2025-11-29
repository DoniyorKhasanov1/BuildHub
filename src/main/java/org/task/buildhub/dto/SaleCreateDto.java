package org.task.buildhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaleCreateDto {
    private List<SaleItemCreateDto> items;
    private String paymentMethod;
    private String customerName;
    private String customerPhone;
}