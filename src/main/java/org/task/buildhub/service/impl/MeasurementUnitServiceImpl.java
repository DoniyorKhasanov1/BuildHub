package org.task.buildhub.service.impl;

import org.task.buildhub.dto.MeasurementUnitCreateDto;
import org.task.buildhub.dto.MeasurementUnitDto;
import org.task.buildhub.entity.MeasurementUnit;
import org.task.buildhub.repository.MeasurementUnitRepository;
import org.task.buildhub.service.MeasurementUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasurementUnitServiceImpl implements MeasurementUnitService {
    private final MeasurementUnitRepository measurementUnitRepository;

    @Override
    public List<MeasurementUnitDto> getAllUnits() {
        return measurementUnitRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MeasurementUnitDto getUnitById(Long id) {
        var unit = measurementUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Measurement unit not found"));
        return convertToDto(unit);
    }

    @Override
    public MeasurementUnitDto createUnit(MeasurementUnitCreateDto dto) {
        if (measurementUnitRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Measurement unit with this name already exists");
        }

        var unit = MeasurementUnit.builder()
                .name(dto.getName())
                .symbol(dto.getSymbol())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .conversionFactor(dto.getConversionFactor())
                .isBaseUnit(dto.getIsBaseUnit() != null ? dto.getIsBaseUnit() : false)
                .build();

        if (dto.getBaseUnitId() != null) {
            var baseUnit = measurementUnitRepository.findById(dto.getBaseUnitId())
                    .orElseThrow(() -> new RuntimeException("Base unit not found"));
            unit.setBaseUnit(baseUnit);
        }

        var savedUnit = measurementUnitRepository.save(unit);
        return convertToDto(savedUnit);
    }

    @Override
    public MeasurementUnitDto updateUnit(Long id, MeasurementUnitCreateDto dto) {
        var unit = measurementUnitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Measurement unit not found"));

        unit.setName(dto.getName());
        unit.setSymbol(dto.getSymbol());
        unit.setCategory(dto.getCategory());
        unit.setDescription(dto.getDescription());
        unit.setConversionFactor(dto.getConversionFactor());

        if (dto.getBaseUnitId() != null) {
            var baseUnit = measurementUnitRepository.findById(dto.getBaseUnitId())
                    .orElseThrow(() -> new RuntimeException("Base unit not found"));
            unit.setBaseUnit(baseUnit);
        } else {
            unit.setBaseUnit(null);
        }

        if (dto.getIsBaseUnit() != null) {
            unit.setIsBaseUnit(dto.getIsBaseUnit());
        }

        var updatedUnit = measurementUnitRepository.save(unit);
        return convertToDto(updatedUnit);
    }

    @Override
    public void deleteUnit(Long id) {
        measurementUnitRepository.deleteById(id);
    }

    @Override
    public List<MeasurementUnitDto> getBaseUnits() {
        return measurementUnitRepository.findByIsBaseUnitTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double convertUnit(Long fromUnitId, Long toUnitId, Double quantity) {
        var fromUnit = measurementUnitRepository.findById(fromUnitId)
                .orElseThrow(() -> new RuntimeException("From unit not found"));
        var toUnit = measurementUnitRepository.findById(toUnitId)
                .orElseThrow(() -> new RuntimeException("To unit not found"));

        if (fromUnit.getBaseUnit() == null || toUnit.getBaseUnit() == null) {
            throw new RuntimeException("Units must have base units for conversion");
        }

        if (!fromUnit.getBaseUnit().getId().equals(toUnit.getBaseUnit().getId())) {
            throw new RuntimeException("Cannot convert between different base units");
        }

        var fromFactor = fromUnit.getConversionFactor();
        var toFactor = toUnit.getConversionFactor();

        return (quantity * fromFactor) / toFactor;
    }

    private MeasurementUnitDto convertToDto(MeasurementUnit unit) {
        var dto = new MeasurementUnitDto();
        dto.setId(unit.getId());
        dto.setName(unit.getName());
        dto.setSymbol(unit.getSymbol());
        dto.setCategory(unit.getCategory());
        dto.setDescription(unit.getDescription());
        dto.setConversionFactor(unit.getConversionFactor());
        dto.setIsBaseUnit(unit.getIsBaseUnit());

        if (unit.getBaseUnit() != null) {
            dto.setBaseUnitId(unit.getBaseUnit().getId());
            dto.setBaseUnitName(unit.getBaseUnit().getName());
        }

        return dto;
    }
}