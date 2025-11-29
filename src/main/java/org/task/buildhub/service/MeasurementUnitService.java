package org.task.buildhub.service;

import org.task.buildhub.dto.MeasurementUnitCreateDto;
import org.task.buildhub.dto.MeasurementUnitDto;

import java.util.List;

public interface MeasurementUnitService {
    List<MeasurementUnitDto> getAllUnits();
    MeasurementUnitDto getUnitById(Long id);
    MeasurementUnitDto createUnit(MeasurementUnitCreateDto dto);
    MeasurementUnitDto updateUnit(Long id, MeasurementUnitCreateDto dto);
    void deleteUnit(Long id);
    List<MeasurementUnitDto> getBaseUnits();
    Double convertUnit(Long fromUnitId, Long toUnitId, Double quantity);
}