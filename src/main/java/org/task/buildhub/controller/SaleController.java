package org.task.buildhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.buildhub.dto.SaleCreateDto;
import org.task.buildhub.dto.SaleDto;
import org.task.buildhub.service.SaleService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Sales management")
public class SaleController {
    private final SaleService saleService;

    @GetMapping
    @Operation(summary = "Get all sales")
    public ResponseEntity<List<SaleDto>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID")
    public ResponseEntity<SaleDto> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @GetMapping("/receipt/{receiptNumber}")
    @Operation(summary = "Get sale by receipt number")
    public ResponseEntity<SaleDto> getSaleByReceiptNumber(@PathVariable String receiptNumber) {
        return ResponseEntity.ok(saleService.getSaleByReceiptNumber(receiptNumber));
    }

    @GetMapping("/period")
    @Operation(summary = "Get sales by period")
    public ResponseEntity<List<SaleDto>> getSalesByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(saleService.getSalesByPeriod(start, end));
    }

    @GetMapping("/cashier/{cashierId}")
    @Operation(summary = "Get sales by cashier")
    public ResponseEntity<List<SaleDto>> getSalesByCashier(@PathVariable Long cashierId) {
        return ResponseEntity.ok(saleService.getSalesByCashier(cashierId));
    }

    @PostMapping("/pos")
    @Operation(summary = "Create new sale (POS)")
    public ResponseEntity<SaleDto> createSale(@Valid @RequestBody SaleCreateDto dto) {
        return ResponseEntity.ok(saleService.createSale(dto));
    }

    @GetMapping("/stats/period")
    @Operation(summary = "Get sales statistics for period")
    public ResponseEntity<Double> getTotalSalesByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(saleService.getTotalSalesByPeriod(start, end));
    }

    @GetMapping("/stats/average")
    @Operation(summary = "Get average sale for period")
    public ResponseEntity<Double> getAverageSaleByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(saleService.getAverageSaleByPeriod(start, end));
    }
}