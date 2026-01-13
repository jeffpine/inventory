package org.pine.inventory.repository;

import org.pine.inventory.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    @Query("""
        SELECT p FROM Product p
        WHERE (:categoryId IS NULL OR p.category.id = :categoryId)
        AND (:supplierId IS NULL OR p.supplier.id = :supplierId)
        AND (:active IS NULL OR p.active = :active)
        AND (:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')))
    """)
    Page<Product> search(
            @Param("categoryId") Long categoryId,
            @Param("supplierId") Long supplierId,
            @Param("active") Boolean active,
            @Param("q") String q,
            Pageable pageable
    );

    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= p.minStockThreshold")
    
    List<Product> findLowStock();
}