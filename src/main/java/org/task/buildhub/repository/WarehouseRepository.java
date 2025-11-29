package org.task.buildhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.task.buildhub.entity.Warehouse;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByActiveTrue();

    @Query("SELECT w FROM Warehouse w WHERE w.name LIKE %:search% OR w.address LIKE %:search%")
    List<Warehouse> searchWarehouses(String search);
}