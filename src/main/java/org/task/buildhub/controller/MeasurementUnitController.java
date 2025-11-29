package org.task.buildhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.buildhub.dto.MeasurementUnitCreateDto;
import org.task.buildhub.dto.MeasurementUnitDto;
import org.task.buildhub.service.MeasurementUnitService;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
@Tag(name = "Measurement Units", description = "Measurement units management")
public class MeasurementUnitController {
    private final MeasurementUnitService measurementUnitService;

    @GetMapping
    @Operation(summary = "Get all measurement units")
    public ResponseEntity<List<MeasurementUnitDto>> getAllUnits() {
        return ResponseEntity.ok(measurementUnitService.getAllUnits());
    }

    @GetMapping("/base")
    @Operation(summary = "Get base measurement units")
    public ResponseEntity<List<MeasurementUnitDto>> getBaseUnits() {
        return ResponseEntity.ok(measurementUnitService.getBaseUnits());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get measurement unit by ID")
    public ResponseEntity<MeasurementUnitDto> getUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(measurementUnitService.getUnitById(id));
    }

    @PostMapping
    @Operation(summary = "Create new measurement unit")
    public ResponseEntity<MeasurementUnitDto> createUnit(@Valid @RequestBody MeasurementUnitCreateDto dto) {
        return ResponseEntity.ok(measurementUnitService.createUnit(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update measurement unit")
    public ResponseEntity<MeasurementUnitDto> updateUnit(@PathVariable Long id, @Valid @RequestBody MeasurementUnitCreateDto dto) {
        return ResponseEntity.ok(measurementUnitService.updateUnit(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete measurement unit")
    public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
        measurementUnitService.deleteUnit(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert between units")
    public ResponseEntity<Double> convertUnit(
            @RequestParam Long fromUnitId,
            @RequestParam Long toUnitId,
            @RequestParam Double quantity) {
        var result = measurementUnitService.convertUnit(fromUnitId, toUnitId, quantity);
        return ResponseEntity.ok(result);
    }
}