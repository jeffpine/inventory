package org.pine.inventory.service;

import org.pine.inventory.domain.order.Order;
import org.pine.inventory.domain.order.OrderStatus;
import org.pine.inventory.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(Order order) {
        order.getItems().forEach(item ->
                productService.updateStock(
                        item.getProduct().getId(),
                        -item.getQuantity()
                )
                );
        order.setStatus(OrderStatus.CREATED);
        return orderRepository.save(order);
    }

    public Order updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public Order findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public BigDecimal getTotalSales(
            LocalDateTime start,
            LocalDateTime end
    ) {
        return orderRepository.totalSales(start, end);
    }

    public List<Object[]> getBestSellers(
            LocalDateTime start,
            LocalDateTime end
    ) {
        return orderRepository.bestSellers(start, end);
    }
}
