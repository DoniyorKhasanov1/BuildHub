package org.task.buildhub.service.impl;

import org.task.buildhub.repository.*;
import org.task.buildhub.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final SaleRepository saleRepository;
    private final CreditSaleRepository creditSaleRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public Map<String, Object> getDashboardStats() {
        var stats = new HashMap<String, Object>();

        var startDate = LocalDateTime.now().minusDays(30);
        var endDate = LocalDateTime.now();

        var totalSales = saleRepository.getTotalSalesByPeriod(startDate, endDate);
        var averageSale = saleRepository.getAverageSaleByPeriod(startDate, endDate);
        var totalSalesCount = saleRepository.findBySaleDateBetween(startDate, endDate).size();

        var totalCredit = creditSaleRepository.getTotalCreditAmount();
        var totalPaid = creditSaleRepository.getTotalPaidAmount();
        var overdueCount = creditSaleRepository.getOverdueCount();

        var totalWarehouseValue = stockRepository.getTotalWarehouseValue(1L);

        stats.put("totalRevenue", totalSales != null ? BigDecimal.valueOf(totalSales) : BigDecimal.ZERO);
        stats.put("averageReceipt", averageSale != null ? BigDecimal.valueOf(averageSale) : BigDecimal.ZERO);
        stats.put("totalSales", totalSalesCount);
        stats.put("warehouseValue", totalWarehouseValue != null ? BigDecimal.valueOf(totalWarehouseValue) : BigDecimal.ZERO);
        stats.put("totalCredit", totalCredit != null ? BigDecimal.valueOf(totalCredit) : BigDecimal.ZERO);
        stats.put("totalPaid", totalPaid != null ? BigDecimal.valueOf(totalPaid) : BigDecimal.ZERO);
        stats.put("overdueCount", overdueCount != null ? overdueCount : 0L);

        return stats;
    }

    @Override
    public Map<String, Object> getSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        var report = new HashMap<String, Object>();

        var sales = saleRepository.findBySaleDateBetween(startDate, endDate);
        var totalSales = saleRepository.getTotalSalesByPeriod(startDate, endDate);
        var averageSale = saleRepository.getAverageSaleByPeriod(startDate, endDate);

        report.put("period", Map.of("start", startDate, "end", endDate));
        report.put("totalSales", sales.size());
        report.put("totalRevenue", totalSales != null ? BigDecimal.valueOf(totalSales) : BigDecimal.ZERO);
        report.put("averageSale", averageSale != null ? BigDecimal.valueOf(averageSale) : BigDecimal.ZERO);
        report.put("sales", sales);

        return report;
    }

    @Override
    public List<Map<String, Object>> getTopProductsByRevenue(LocalDateTime startDate, LocalDateTime endDate, int limit) {
        var sales = saleRepository.findBySaleDateBetween(startDate, endDate);
        var productRevenue = new HashMap<Long, BigDecimal>();

        for (var sale : sales) {
            for (var item : sale.getSaleItems()) {
                var productId = item.getProduct().getId();
                var revenue = productRevenue.getOrDefault(productId, BigDecimal.ZERO);
                productRevenue.put(productId, revenue.add(item.getTotal()));
            }
        }

        return productRevenue.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(limit)
                .map(entry -> {
                    var product = productRepository.findById(entry.getKey()).orElse(null);
                    Map<String, Object> result = new HashMap<>();
                    result.put("productId", entry.getKey());
                    result.put("productName", product != null ? product.getName() : "Unknown");
                    result.put("revenue", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getWeeklySalesTrend(LocalDateTime startDate, LocalDateTime endDate) {
        var trend = new ArrayList<Map<String, Object>>();
        var current = startDate;

        while (current.isBefore(endDate) || current.isEqual(endDate)) {
            var weekStart = current.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            var weekEnd = weekStart.plusDays(6);

            var weeklySales = saleRepository.getTotalSalesByPeriod(weekStart, weekEnd);

            Map<String, Object> weekData = new HashMap<>();
            weekData.put("week", weekStart.toLocalDate().toString());
            weekData.put("sales", weeklySales != null ? BigDecimal.valueOf(weeklySales) : BigDecimal.ZERO);

            trend.add(weekData);

            current = weekStart.plusWeeks(1);
        }

        return trend;
    }

    @Override
    public List<Map<String, Object>> getWarehouseInventoryValue() {
        var warehouses = warehouseRepository.findByActiveTrue();
        var inventory = new ArrayList<Map<String, Object>>();

        for (var warehouse : warehouses) {
            var value = stockRepository.getTotalWarehouseValue(warehouse.getId());
            var productCount = stockRepository.getProductCountInWarehouse(warehouse.getId());

            Map<String, Object> warehouseData = new HashMap<>();
            warehouseData.put("warehouseId", warehouse.getId());
            warehouseData.put("warehouseName", warehouse.getName());
            warehouseData.put("totalValue", value != null ? BigDecimal.valueOf(value) : BigDecimal.ZERO);
            warehouseData.put("productCount", productCount != null ? productCount : 0);

            inventory.add(warehouseData);
        }

        return inventory;
    }
}