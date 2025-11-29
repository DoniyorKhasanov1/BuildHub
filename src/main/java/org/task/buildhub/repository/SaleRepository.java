package org.task.buildhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.task.buildhub.entity.Sale;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByReceiptNumber(String receiptNumber);
    List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);
    List<Sale> findByCashierId(Long cashierId);

    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE s.saleDate BETWEEN :start AND :end")
    Double getTotalSalesByPeriod(LocalDateTime start, LocalDateTime end);

    @Query("SELECT AVG(s.totalAmount) FROM Sale s WHERE s.saleDate BETWEEN :start AND :end")
    Double getAverageSaleByPeriod(LocalDateTime start, LocalDateTime end);
}