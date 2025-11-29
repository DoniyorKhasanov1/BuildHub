package org.task.buildhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.task.buildhub.dto.WarehouseCreateDto;
import org.task.buildhub.dto.WarehouseDto;
import org.task.buildhub.entity.Warehouse;
import org.task.buildhub.repository.StockRepository;
import org.task.buildhub.repository.WarehouseRepository;
import org.task.buildhub.service.WarehouseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final StockRepository stockRepository;

    @Override
    public List<WarehouseDto> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WarehouseDto getWarehouseById(Long id) {
        var warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return convertToDto(warehouse);
    }

    @Override
    public WarehouseDto createWarehouse(WarehouseCreateDto dto) {
        var warehouse = Warehouse.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .description(dto.getDescription())
                .active(true)
                .build();

        var savedWarehouse = warehouseRepository.save(warehouse);
        return convertToDto(savedWarehouse);
    }

    @Override
    public WarehouseDto updateWarehouse(Long id, WarehouseCreateDto dto) {
        var warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setDescription(dto.getDescription());

        var updatedWarehouse = warehouseRepository.save(warehouse);
        return convertToDto(updatedWarehouse);
    }

    @Override
    public void deleteWarehouse(Long id) {
        var warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouse.setActive(false);
        warehouseRepository.save(warehouse);
    }

    @Override
    public List<WarehouseDto> searchWarehouses(String search) {
        return warehouseRepository.searchWarehouses(search).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private WarehouseDto convertToDto(Warehouse warehouse) {
        var dto = new WarehouseDto();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setAddress(warehouse.getAddress());
        dto.setDescription(warehouse.getDescription());
        dto.setActive(warehouse.isActive());

        var productCount = stockRepository.getProductCountInWarehouse(warehouse.getId());
        var totalValue = stockRepository.getTotalWarehouseValue(warehouse.getId());

        dto.setProductCount(productCount != null ? productCount : 0);
        dto.setTotalValue(totalValue != null ? totalValue.longValue() : 0L);

        return dto;
    }
}