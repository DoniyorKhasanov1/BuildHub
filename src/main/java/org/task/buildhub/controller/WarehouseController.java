package org.task.buildhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.buildhub.dto.WarehouseCreateDto;
import org.task.buildhub.dto.WarehouseDto;
import org.task.buildhub.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@Tag(name = "Warehouses", description = "Warehouses management")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    @Operation(summary = "Get all warehouses")
    public ResponseEntity<List<WarehouseDto>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get warehouse by ID")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search warehouses")
    public ResponseEntity<List<WarehouseDto>> searchWarehouses(@RequestParam String q) {
        return ResponseEntity.ok(warehouseService.searchWarehouses(q));
    }

    @PostMapping
    @Operation(summary = "Create new warehouse")
    public ResponseEntity<WarehouseDto> createWarehouse(@Valid @RequestBody WarehouseCreateDto dto) {
        return ResponseEntity.ok(warehouseService.createWarehouse(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update warehouse")
    public ResponseEntity<WarehouseDto> updateWarehouse(@PathVariable Long id, @Valid @RequestBody WarehouseCreateDto dto) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete warehouse")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }
}