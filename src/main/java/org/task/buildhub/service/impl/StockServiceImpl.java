package org.task.buildhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.task.buildhub.dto.StockDto;
import org.task.buildhub.dto.StockUpdateDto;
import org.task.buildhub.entity.Stock;
import org.task.buildhub.repository.ProductRepository;
import org.task.buildhub.repository.StockRepository;
import org.task.buildhub.repository.WarehouseRepository;
import org.task.buildhub.service.StockService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<StockDto> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockDto getStockById(Long id) {
        var stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        return convertToDto(stock);
    }

    @Override
    public StockDto getStockByProductAndWarehouse(Long productId, Long warehouseId) {
        var stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        return convertToDto(stock);
    }

    @Override
    public StockDto updateStock(Long productId, Long warehouseId, StockUpdateDto dto) {
        var stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        stock.setQuantity(dto.getQuantity());
        if (dto.getCostPrice() != null) {
            stock.setCostPrice(dto.getCostPrice());
        }

        var updatedStock = stockRepository.save(stock);
        return convertToDto(updatedStock);
    }

    @Override
    public StockDto addStock(Long productId, Long warehouseId, Integer quantity, Double costPrice) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        var warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        var stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElse(Stock.builder()
                        .product(product)
                        .warehouse(warehouse)
                        .quantity(0)
                        .costPrice(BigDecimal.ZERO)
                        .build());

        stock.setQuantity(stock.getQuantity() + quantity);
        if (costPrice != null) {
            stock.setCostPrice(BigDecimal.valueOf(costPrice));
        }

        var savedStock = stockRepository.save(stock);
        return convertToDto(savedStock);
    }

    @Override
    public List<StockDto> getStocksByWarehouse(Long warehouseId) {
        return stockRepository.findByWarehouseId(warehouseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockDto> getLowStockItems() {
        return stockRepository.findLowStockItems().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getWarehouseTotalValue(Long warehouseId) {
        return stockRepository.getTotalWarehouseValue(warehouseId);
    }

    private StockDto convertToDto(Stock stock) {
        var dto = new StockDto();
        dto.setId(stock.getId());
        dto.setProductId(stock.getProduct().getId());
        dto.setProductName(stock.getProduct().getName());
        dto.setProductSku(stock.getProduct().getSku());
        dto.setWarehouseId(stock.getWarehouse().getId());
        dto.setWarehouseName(stock.getWarehouse().getName());
        dto.setQuantity(stock.getQuantity());
        dto.setCostPrice(stock.getCostPrice());

        var totalValue = stock.getCostPrice().multiply(BigDecimal.valueOf(stock.getQuantity()));
        dto.setTotalValue(totalValue);

        return dto;
    }
}