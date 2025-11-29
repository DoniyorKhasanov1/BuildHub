package org.task.buildhub.service;

import org.task.buildhub.dto.SaleCreateDto;
import org.task.buildhub.dto.SaleDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {
    List<SaleDto> getAllSales();
    SaleDto getSaleById(Long id);
    SaleDto getSaleByReceiptNumber(String receiptNumber);
    SaleDto createSale(SaleCreateDto dto);
    List<SaleDto> getSalesByPeriod(LocalDateTime start, LocalDateTime end);
    List<SaleDto> getSalesByCashier(Long cashierId);
    Double getTotalSalesByPeriod(LocalDateTime start, LocalDateTime end);
    Double getAverageSaleByPeriod(LocalDateTime start, LocalDateTime end);
}