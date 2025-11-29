package org.task.buildhub.service;

import org.task.buildhub.dto.CreditSaleCreateDto;
import org.task.buildhub.dto.CreditSaleDto;
import org.task.buildhub.dto.PaymentDto;

import java.util.List;

public interface CreditSaleService {
    List<CreditSaleDto> getAllCreditSales();
    CreditSaleDto getCreditSaleById(Long id);
    CreditSaleDto createCreditSale(CreditSaleCreateDto dto);
    CreditSaleDto addPayment(Long creditSaleId, PaymentDto paymentDto);
    List<CreditSaleDto> getCreditSalesByStatus(String status);
    List<CreditSaleDto> getOverdueCreditSales();
    Double getTotalCreditAmount();
    Double getTotalPaidAmount();
    Long getOverdueCount();
}