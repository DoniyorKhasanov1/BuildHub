package org.task.buildhub.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.buildhub.entity.MeasurementUnit;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long> {
    List<MeasurementUnit> findByIsBaseUnitTrue();
    List<MeasurementUnit> findByBaseUnitId(Long baseUnitId);
    Optional<MeasurementUnit> findByName(String name);
    Optional<MeasurementUnit> findBySymbol(String symbol);
}