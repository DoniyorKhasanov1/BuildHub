package org.task.buildhub.dto;

import lombok.Data;

@Data
public class SaleItemCreateDto {
    private Long productId;
    private Integer quantity;
}