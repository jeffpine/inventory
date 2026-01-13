package org.pine.inventory.service;


import org.pine.inventory.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyLowStock(Product saved) {
        System.out.println("Alerta: Produto com estoque baixo -> " + saved.getName() + "| Quantidade: " + saved.getStockQuantity());
    }
}