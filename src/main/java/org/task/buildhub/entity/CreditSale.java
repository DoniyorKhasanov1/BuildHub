package org.task.buildhub.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "paid_amount")
    private BigDecimal paidAmount;

    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;

    @Column(name = "due_date")
    private LocalDate dueDate;

    private String status;

    private String customerName;
    private String customerPhone;
    private String customerAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (remainingAmount == null) {
            remainingAmount = totalAmount.subtract(paidAmount != null ? paidAmount : BigDecimal.ZERO);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        remainingAmount = totalAmount.subtract(paidAmount != null ? paidAmount : BigDecimal.ZERO);

        if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
            status = "PAID";
        } else if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
            status = "OVERDUE";
        } else if (paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            status = "PARTIAL";
        } else {
            status = "PENDING";
        }
    }
}