package org.task.buildhub.dto;

import lombok.Data;

@Data
public class WarehouseCreateDto {
    private String name;
    private String address;
    private String description;
}