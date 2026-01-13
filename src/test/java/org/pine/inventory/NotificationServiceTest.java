package org.pine.inventory;

import org.junit.jupiter.api.Test;
import org.pine.inventory.domain.Product;
import org.pine.inventory.service.NotificationService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationServiceTest {

    @Test
    void testNotifyLowStock() {
        NotificationService notificationService = new NotificationService();
        Product product = new Product();
        product.setName("Test Product");
        product.setStockQuantity(2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        notificationService.notifyLowStock(product);

        String expectedOutput = "Alerta: Produto com estoque baixo -> Test Product| Quantidade: 2";
        assertTrue(outContent.toString().trim().contains(expectedOutput));

        System.setOut(System.out);
    }
}
