package org.task.buildhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.task.buildhub.dto.CreditSaleCreateDto;
import org.task.buildhub.dto.CreditSaleDto;
import org.task.buildhub.dto.PaymentDto;
import org.task.buildhub.entity.CreditSale;
import org.task.buildhub.repository.CreditSaleRepository;
import org.task.buildhub.repository.SaleRepository;
import org.task.buildhub.service.CreditSaleService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditSaleServiceImpl implements CreditSaleService {
    private final CreditSaleRepository creditSaleRepository;
    private final SaleRepository saleRepository;

    @Override
    public CreditSaleDto createCreditSale(CreditSaleCreateDto dto) {
        var sale = saleRepository.findById(dto.getSaleId())
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        var creditSale = CreditSale.builder()
                .sale(sale)
                .totalAmount(sale.getTotalAmount())
                .paidAmount(BigDecimal.ZERO)
                .remainingAmount(sale.getTotalAmount())
                .dueDate(dto.getDueDate())
                .status("PENDING")
                .customerName(dto.getCustomerName())
                .customerPhone(dto.getCustomerPhone())
                .customerAddress(dto.getCustomerAddress())
                .build();

        var savedCreditSale = creditSaleRepository.save(creditSale);
        return convertToDto(savedCreditSale);
    }

    @Override
    public CreditSaleDto addPayment(Long creditSaleId, PaymentDto paymentDto) {
        var creditSale = creditSaleRepository.findById(creditSaleId)
                .orElseThrow(() -> new RuntimeException("Credit sale not found"));

        var newPaidAmount = creditSale.getPaidAmount().add(paymentDto.getAmount());
        creditSale.setPaidAmount(newPaidAmount);

        var updatedCreditSale = creditSaleRepository.save(creditSale);
        return convertToDto(updatedCreditSale);
    }

    @Override
    public List<CreditSaleDto> getAllCreditSales() {
        return creditSaleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CreditSaleDto getCreditSaleById(Long id) {
        var creditSale = creditSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Credit sale not found"));
        return convertToDto(creditSale);
    }

    @Override
    public List<CreditSaleDto> getCreditSalesByStatus(String status) {
        return creditSaleRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditSaleDto> getOverdueCreditSales() {
        return creditSaleRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), "PAID").stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalCreditAmount() {
        return creditSaleRepository.getTotalCreditAmount();
    }

    @Override
    public Double getTotalPaidAmount() {
        return creditSaleRepository.getTotalPaidAmount();
    }

    @Override
    public Long getOverdueCount() {
        return creditSaleRepository.getOverdueCount();
    }

    private CreditSaleDto convertToDto(CreditSale creditSale) {
        var dto = new CreditSaleDto();
        dto.setId(creditSale.getId());
        dto.setSaleId(creditSale.getSale().getId());
        dto.setReceiptNumber(creditSale.getSale().getReceiptNumber());
        dto.setTotalAmount(creditSale.getTotalAmount());
        dto.setPaidAmount(creditSale.getPaidAmount());
        dto.setRemainingAmount(creditSale.getRemainingAmount());
        dto.setDueDate(creditSale.getDueDate());
        dto.setStatus(creditSale.getStatus());
        dto.setCustomerName(creditSale.getCustomerName());
        dto.setCustomerPhone(creditSale.getCustomerPhone());
        dto.setCustomerAddress(creditSale.getCustomerAddress());
        return dto;
    }
}