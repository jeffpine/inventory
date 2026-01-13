package org.pine.inventory.service;

import org.pine.inventory.domain.Product;
import org.pine.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ProductRepository productRepository;

    public ReportService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> lowStockReport() {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getStockQuantity() <= p.getMinStockThreshold())
                .toList();
    }

    public long totalProducts() {
        return productRepository.count();
    }
}