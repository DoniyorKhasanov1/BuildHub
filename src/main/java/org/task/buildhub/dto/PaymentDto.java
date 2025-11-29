package org.task.buildhub.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private BigDecimal amount;
    private String paymentMethod;
}