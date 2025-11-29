package org.task.buildhub.dto;

import lombok.Data;

@Data
public class MeasurementUnitCreateDto {
    private String name;
    private String symbol;
    private String category;
    private String description;
    private Long baseUnitId;
    private Double conversionFactor;
    private Boolean isBaseUnit;
}

