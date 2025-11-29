package org.task.buildhub.dto;

import lombok.Data;

@Data
public class WarehouseDto {
    private Long id;
    private String name;
    private String address;
    private String description;
    private Boolean active;
    private Integer productCount;
    private Long totalValue;
}
