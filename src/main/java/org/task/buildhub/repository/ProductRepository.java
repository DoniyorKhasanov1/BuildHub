package org.task.buildhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.task.buildhub.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    Optional<Product> findByBarcode(String barcode);
    List<Product> findByActiveTrue();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:search% OR p.sku LIKE %:search% OR p.barcode LIKE %:search%")
    List<Product> searchProducts(String search);
}