package org.task.buildhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "measurement_units")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String symbol;

    private String category;
    private String description;

    @ManyToOne
    @JoinColumn(name = "base_unit_id")
    private MeasurementUnit baseUnit;

    private Double conversionFactor;

    @Builder.Default
    @Column(name = "is_base_unit")
    private boolean isBaseUnit = false;

    public boolean getIsBaseUnit() {
        return isBaseUnit;
    }

    public void setIsBaseUnit(boolean isBaseUnit) {
        this.isBaseUnit = isBaseUnit;
    }
}