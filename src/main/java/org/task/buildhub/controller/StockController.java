package org.task.buildhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.buildhub.dto.StockDto;
import org.task.buildhub.dto.StockUpdateDto;
import org.task.buildhub.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Tag(name = "Stocks", description = "Stock management")
public class StockController {
    private final StockService stockService;

    @GetMapping
    @Operation(summary = "Get all stocks")
    public ResponseEntity<List<StockDto>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/warehouse/{warehouseId}")
    @Operation(summary = "Get stocks by warehouse")
    public ResponseEntity<List<StockDto>> getStocksByWarehouse(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(stockService.getStocksByWarehouse(warehouseId));
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock items")
    public ResponseEntity<List<StockDto>> getLowStockItems() {
        return ResponseEntity.ok(stockService.getLowStockItems());
    }

    @GetMapping("/product/{productId}/warehouse/{warehouseId}")
    @Operation(summary = "Get stock by product and warehouse")
    public ResponseEntity<StockDto> getStockByProductAndWarehouse(
            @PathVariable Long productId, @PathVariable Long warehouseId) {
        return ResponseEntity.ok(stockService.getStockByProductAndWarehouse(productId, warehouseId));
    }

    @PutMapping("/product/{productId}/warehouse/{warehouseId}")
    @Operation(summary = "Update stock")
    public ResponseEntity<StockDto> updateStock(
            @PathVariable Long productId, @PathVariable Long warehouseId,
            @Valid @RequestBody StockUpdateDto dto) {
        return ResponseEntity.ok(stockService.updateStock(productId, warehouseId, dto));
    }

    @PostMapping("/product/{productId}/warehouse/{warehouseId}/add")
    @Operation(summary = "Add stock quantity")
    public ResponseEntity<StockDto> addStock(
            @PathVariable Long productId, @PathVariable Long warehouseId,
            @RequestParam Integer quantity, @RequestParam(required = false) Double costPrice) {
        return ResponseEntity.ok(stockService.addStock(productId, warehouseId, quantity, costPrice));
    }

    @GetMapping("/warehouse/{warehouseId}/total-value")
    @Operation(summary = "Get warehouse total value")
    public ResponseEntity<Double> getWarehouseTotalValue(@PathVariable Long warehouseId) {
        return ResponseEntity.ok(stockService.getWarehouseTotalValue(warehouseId));
    }
}