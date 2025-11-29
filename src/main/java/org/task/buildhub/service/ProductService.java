package org.task.buildhub.service;

import org.task.buildhub.dto.ProductCreateDto;
import org.task.buildhub.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto getProductBySku(String sku);
    ProductDto getProductByBarcode(String barcode);
    ProductDto createProduct(ProductCreateDto dto);
    ProductDto updateProduct(Long id, ProductCreateDto dto);
    void deleteProduct(Long id);
    List<ProductDto> searchProducts(String search);
    List<ProductDto> getActiveProducts();
}