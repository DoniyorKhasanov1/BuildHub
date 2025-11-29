package org.task.buildhub.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<String, Object> getDashboardStats();
    Map<String, Object> getSalesReport(LocalDateTime startDate, LocalDateTime endDate);
    List<Map<String, Object>> getTopProductsByRevenue(LocalDateTime startDate, LocalDateTime endDate, int limit);
    List<Map<String, Object>> getWeeklySalesTrend(LocalDateTime startDate, LocalDateTime endDate);
    List<Map<String, Object>> getWarehouseInventoryValue();
}