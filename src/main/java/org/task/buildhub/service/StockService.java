package org.task.buildhub.service;

import org.task.buildhub.dto.StockDto;
import org.task.buildhub.dto.StockUpdateDto;

import java.util.List;

public interface StockService {
    List<StockDto> getAllStocks();
    StockDto getStockById(Long id);
    StockDto getStockByProductAndWarehouse(Long productId, Long warehouseId);
    StockDto updateStock(Long productId, Long warehouseId, StockUpdateDto dto);
    StockDto addStock(Long productId, Long warehouseId, Integer quantity, Double costPrice);
    List<StockDto> getStocksByWarehouse(Long warehouseId);
    List<StockDto> getLowStockItems();
    Double getWarehouseTotalValue(Long warehouseId);
}