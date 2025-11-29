package org.task.buildhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.task.buildhub.dto.ProductCreateDto;
import org.task.buildhub.dto.ProductDto;
import org.task.buildhub.entity.Product;
import org.task.buildhub.repository.MeasurementUnitRepository;
import org.task.buildhub.repository.ProductRepository;
import org.task.buildhub.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MeasurementUnitRepository measurementUnitRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    @Override
    public ProductDto getProductBySku(String sku) {
        var product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    @Override
    public ProductDto getProductByBarcode(String barcode) {
        var product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

    @Override
    public ProductDto createProduct(ProductCreateDto dto) {
        if (productRepository.findBySku(dto.getSku()).isPresent()) {
            throw new RuntimeException("Product with this SKU already exists");
        }

        var unit = measurementUnitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new RuntimeException("Measurement unit not found"));

        var product = Product.builder()
                .sku(dto.getSku())
                .name(dto.getName())
                .description(dto.getDescription())
                .barcode(dto.getBarcode())
                .brand(dto.getBrand())
                .unit(unit)
                .retailPrice(dto.getRetailPrice())
                .wholesalePrice(dto.getWholesalePrice())
                .minStockLevel(dto.getMinStockLevel())
                .active(true)
                .build();

        var savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductCreateDto dto) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        var unit = measurementUnitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new RuntimeException("Measurement unit not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBarcode(dto.getBarcode());
        product.setBrand(dto.getBrand());
        product.setUnit(unit);
        product.setRetailPrice(dto.getRetailPrice());
        product.setWholesalePrice(dto.getWholesalePrice());
        product.setMinStockLevel(dto.getMinStockLevel());

        var updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> searchProducts(String search) {
        return productRepository.searchProducts(search).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getActiveProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        var dto = new ProductDto();
        dto.setId(product.getId());
        dto.setSku(product.getSku());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBarcode(product.getBarcode());
        dto.setBrand(product.getBrand());
        dto.setUnitId(product.getUnit().getId());
        dto.setUnitName(product.getUnit().getName());
        dto.setUnitSymbol(product.getUnit().getSymbol());
        dto.setRetailPrice(product.getRetailPrice());
        dto.setWholesalePrice(product.getWholesalePrice());
        dto.setMinStockLevel(product.getMinStockLevel());
        dto.setActive(product.isActive());
        return dto;
    }
}