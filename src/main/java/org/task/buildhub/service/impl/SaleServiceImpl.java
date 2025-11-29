package org.task.buildhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.task.buildhub.dto.SaleCreateDto;
import org.task.buildhub.dto.SaleDto;
import org.task.buildhub.dto.SaleItemCreateDto;
import org.task.buildhub.dto.SaleItemDto;
import org.task.buildhub.entity.Sale;
import org.task.buildhub.entity.SaleItem;
import org.task.buildhub.repository.ProductRepository;
import org.task.buildhub.repository.SaleRepository;
import org.task.buildhub.repository.StockRepository;
import org.task.buildhub.service.SaleService;
import org.task.buildhub.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final UserService userService;

    @Value("${app.tax-rate}")
    private Double taxRate;

    @Override
    @Transactional
    public SaleDto createSale(SaleCreateDto dto) {
        var cashier = userService.getCurrentUser();
        var receiptNumber = generateReceiptNumber();

        var sale = Sale.builder()
                .receiptNumber(receiptNumber)
                .cashier(cashier)
                .paymentMethod(dto.getPaymentMethod())
                .customerName(dto.getCustomerName())
                .customerPhone(dto.getCustomerPhone())
                .saleItems(new ArrayList<>())
                .build();

        BigDecimal subTotal = BigDecimal.ZERO;

        for (SaleItemCreateDto itemDto : dto.getItems()) {
            var product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            var stock = stockRepository.findByProductIdAndWarehouseId(itemDto.getProductId(), 1L)
                    .orElseThrow(() -> new RuntimeException("Product out of stock"));

            if (stock.getQuantity() < itemDto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            stock.setQuantity(stock.getQuantity() - itemDto.getQuantity());
            stockRepository.save(stock);

            var itemTotal = product.getRetailPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            subTotal = subTotal.add(itemTotal);

            var saleItem = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(product.getRetailPrice())
                    .total(itemTotal)
                    .build();

            sale.getSaleItems().add(saleItem);
        }

        var taxAmount = subTotal.multiply(BigDecimal.valueOf(taxRate / 100));
        var totalAmount = subTotal.add(taxAmount);

        sale.setSubTotal(subTotal);
        sale.setTaxAmount(taxAmount);
        sale.setTotalAmount(totalAmount);

        var savedSale = saleRepository.save(sale);
        return convertToDto(savedSale);
    }

    @Override
    public List<SaleDto> getAllSales() {
        return saleRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SaleDto getSaleById(Long id) {
        var sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
        return convertToDto(sale);
    }

    @Override
    public SaleDto getSaleByReceiptNumber(String receiptNumber) {
        var sale = saleRepository.findByReceiptNumber(receiptNumber)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
        return convertToDto(sale);
    }

    @Override
    public List<SaleDto> getSalesByPeriod(LocalDateTime start, LocalDateTime end) {
        return saleRepository.findBySaleDateBetween(start, end).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDto> getSalesByCashier(Long cashierId) {
        return saleRepository.findByCashierId(cashierId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalSalesByPeriod(LocalDateTime start, LocalDateTime end) {
        return saleRepository.getTotalSalesByPeriod(start, end);
    }

    @Override
    public Double getAverageSaleByPeriod(LocalDateTime start, LocalDateTime end) {
        return saleRepository.getAverageSaleByPeriod(start, end);
    }

    private String generateReceiptNumber() {
        return "CHK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private SaleDto convertToDto(Sale sale) {
        var dto = new SaleDto();
        dto.setId(sale.getId());
        dto.setReceiptNumber(sale.getReceiptNumber());
        dto.setCashierId(sale.getCashier().getId());
        dto.setCashierName(sale.getCashier().getFullName());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setTaxAmount(sale.getTaxAmount());
        dto.setSubTotal(sale.getSubTotal());
        dto.setPaymentMethod(sale.getPaymentMethod());
        dto.setCustomerName(sale.getCustomerName());
        dto.setCustomerPhone(sale.getCustomerPhone());
        dto.setSaleDate(sale.getSaleDate());

        var itemDtos = sale.getSaleItems().stream()
                .map(item -> {
                    var itemDto = new SaleItemDto();
                    itemDto.setId(item.getId());
                    itemDto.setProductId(item.getProduct().getId());
                    itemDto.setProductName(item.getProduct().getName());
                    itemDto.setProductSku(item.getProduct().getSku());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setUnitPrice(item.getUnitPrice());
                    itemDto.setTotal(item.getTotal());
                    return itemDto;
                })
                .collect(Collectors.toList());

        dto.setSaleItems(itemDtos);
        return dto;
    }
}