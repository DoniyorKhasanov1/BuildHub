package org.task.buildhub.dto;


import lombok.Data;

@Data
public class MeasurementUnitDto {
    private Long id;
    private String name;
    private String symbol;
    private String category;
    private String description;
    private Long baseUnitId;
    private String baseUnitName;
    private Double conversionFactor;
    private Boolean isBaseUnit;
}
