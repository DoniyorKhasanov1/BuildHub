package org.task.buildhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.buildhub.dto.CreditSaleCreateDto;
import org.task.buildhub.dto.CreditSaleDto;
import org.task.buildhub.dto.PaymentDto;
import org.task.buildhub.service.CreditSaleService;

import java.util.List;

@RestController
@RequestMapping("/api/credit-sales")
@RequiredArgsConstructor
@Tag(name = "Credit Sales", description = "Credit sales management")
public class CreditSaleController {
    private final CreditSaleService creditSaleService;

    @GetMapping
    @Operation(summary = "Get all credit sales")
    public ResponseEntity<List<CreditSaleDto>> getAllCreditSales() {
        return ResponseEntity.ok(creditSaleService.getAllCreditSales());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get credit sale by ID")
    public ResponseEntity<CreditSaleDto> getCreditSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(creditSaleService.getCreditSaleById(id));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get credit sales by status")
    public ResponseEntity<List<CreditSaleDto>> getCreditSalesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(creditSaleService.getCreditSalesByStatus(status));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue credit sales")
    public ResponseEntity<List<CreditSaleDto>> getOverdueCreditSales() {
        return ResponseEntity.ok(creditSaleService.getOverdueCreditSales());
    }

    @PostMapping
    @Operation(summary = "Create new credit sale")
    public ResponseEntity<CreditSaleDto> createCreditSale(@Valid @RequestBody CreditSaleCreateDto dto) {
        return ResponseEntity.ok(creditSaleService.createCreditSale(dto));
    }

    @PostMapping("/{id}/payment")
    @Operation(summary = "Add payment to credit sale")
    public ResponseEntity<CreditSaleDto> addPayment(@PathVariable Long id, @Valid @RequestBody PaymentDto dto) {
        return ResponseEntity.ok(creditSaleService.addPayment(id, dto));
    }

    @GetMapping("/stats/total-credit")
    @Operation(summary = "Get total credit amount")
    public ResponseEntity<Double> getTotalCreditAmount() {
        return ResponseEntity.ok(creditSaleService.getTotalCreditAmount());
    }

    @GetMapping("/stats/total-paid")
    @Operation(summary = "Get total paid amount")
    public ResponseEntity<Double> getTotalPaidAmount() {
        return ResponseEntity.ok(creditSaleService.getTotalPaidAmount());
    }

    @GetMapping("/stats/overdue-count")
    @Operation(summary = "Get overdue count")
    public ResponseEntity<Long> getOverdueCount() {
        return ResponseEntity.ok(creditSaleService.getOverdueCount());
    }
}