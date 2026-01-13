package org.pine.inventory.service;

import org.pine.inventory.domain.Product;
import org.pine.inventory.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public ProductService(ProductRepository productRepository, NotificationService notificationService) {
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }
    public Product create(Product product) {
        return  productRepository.save(product);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.search(categoryId, null, null, null, Pageable.unpaged()).getContent();
    }

    public Product updateStock(Long productId, int quantity) {
        Product product = findById(productId);

        product.setStockQuantity(product.getStockQuantity() + quantity);
        Product saved = productRepository.save(product);

        if (saved.getStockQuantity() <= saved.getMinStockThreshold()) {
            notificationService.notifyLowStock(saved);

        }
        return saved;
    }
}