package org.task.buildhub.service;

import org.task.buildhub.dto.WarehouseCreateDto;
import org.task.buildhub.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {
    List<WarehouseDto> getAllWarehouses();
    WarehouseDto getWarehouseById(Long id);
    WarehouseDto createWarehouse(WarehouseCreateDto dto);
    WarehouseDto updateWarehouse(Long id, WarehouseCreateDto dto);
    void deleteWarehouse(Long id);
    List<WarehouseDto> searchWarehouses(String search);
}