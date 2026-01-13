package org.pine.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pine.inventory.domain.Category;
import org.pine.inventory.domain.Product;
import org.pine.inventory.repository.ProductRepository;
import org.pine.inventory.service.NotificationService;
import org.pine.inventory.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setSku("SKU123");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.findById(1L);

        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
    }

    @Test
    void testFindBySku() {
        Product product = new Product();
        product.setSku("SKU123");

        when(productRepository.findBySku("SKU123")).thenReturn(Optional.of(product));

        Product foundProduct = productService.findBySku("SKU123");

        assertNotNull(foundProduct);
        assertEquals("SKU123", foundProduct.getSku());
    }

    @Test
    void testFindByCategory() {
        Product product = new Product();
        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);

        Page<Product> page = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.search(eq(1L), any(), any(), any(), any(Pageable.class))).thenReturn(page);

        List<Product> products = productService.findByCategory(1L);

        assertFalse(products.isEmpty());
        assertEquals(1L, products.get(0).getCategory().getId());
    }

    @Test
    void testUpdateStock() {
        Product product = new Product();
        product.setId(1L);
        product.setStockQuantity(10);
        product.setMinStockThreshold(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateStock(1L, 5);

        assertEquals(15, updatedProduct.getStockQuantity());
        verify(notificationService, never()).notifyLowStock(any(Product.class));
    }

    @Test
    void testUpdateStockLowStockAlert() {
        Product product = new Product();
        product.setId(1L);
        product.setStockQuantity(10);
        product.setMinStockThreshold(5);
        product.setName("Low Stock Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateStock(1L, -6);

        assertEquals(4, updatedProduct.getStockQuantity());
        verify(notificationService, times(1)).notifyLowStock(product);
    }
}
