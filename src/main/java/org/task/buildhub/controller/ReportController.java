package org.task.buildhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.buildhub.service.ReportService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Reports and analytics")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard statistics")
    public @NonNull ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(reportService.getDashboardStats());
    }

    @GetMapping("/sales")
    @Operation(summary = "Get sales report")
    public ResponseEntity<Map<String, Object>> getSalesReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(reportService.getSalesReport(startDate, endDate));
    }

    @GetMapping("/top-products")
    @Operation(summary = "Get top products by revenue")
    public ResponseEntity<List<Map<String, Object>>> getTopProducts(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(reportService.getTopProductsByRevenue(startDate, endDate, limit));
    }

    @GetMapping("/weekly-trend")
    @Operation(summary = "Get weekly sales trend")
    public ResponseEntity<List<Map<String, Object>>> getWeeklyTrend(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(reportService.getWeeklySalesTrend(startDate, endDate));
    }

    @GetMapping("/warehouse-inventory")
    @Operation(summary = "Get warehouse inventory value")
    public ResponseEntity<List<Map<String, Object>>> getWarehouseInventoryValue() {
        return ResponseEntity.ok(reportService.getWarehouseInventoryValue());
    }
}