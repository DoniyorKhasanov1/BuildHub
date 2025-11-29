package org.task.buildhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.task.buildhub.entity.CreditSale;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CreditSaleRepository extends JpaRepository<CreditSale, Long> {
    List<CreditSale> findByStatus(String status);
    List<CreditSale> findByDueDateBeforeAndStatusNot(LocalDate dueDate, String status);

    @Query("SELECT SUM(cs.totalAmount) FROM CreditSale cs")
    Double getTotalCreditAmount();

    @Query("SELECT SUM(cs.paidAmount) FROM CreditSale cs")
    Double getTotalPaidAmount();

    @Query("SELECT COUNT(cs) FROM CreditSale cs WHERE cs.status = 'OVERDUE'")
    Long getOverdueCount();
}