package org.task.buildhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.task.buildhub.entity.Stock;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
    List<Stock> findByWarehouseId(Long warehouseId);
    List<Stock> findByProductId(Long productId);

    @Query("SELECT s FROM Stock s WHERE s.quantity <= s.product.minStockLevel")
    List<Stock> findLowStockItems();

    @Query("SELECT SUM(s.quantity * s.costPrice) FROM Stock s WHERE s.warehouse.id = :warehouseId")
    Double getTotalWarehouseValue(Long warehouseId);

    @Query("SELECT COUNT(DISTINCT s.product) FROM Stock s WHERE s.warehouse.id = :warehouseId")
    Integer getProductCountInWarehouse(Long warehouseId);
}