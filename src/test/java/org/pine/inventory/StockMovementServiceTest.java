package org.pine.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pine.inventory.domain.Product;
import org.pine.inventory.domain.stock.MovementType;
import org.pine.inventory.domain.stock.StockMovement;
import org.pine.inventory.repository.StockMovementRepository;
import org.pine.inventory.service.StockMovementService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class StockMovementServiceTest {

    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private StockMovementService stockMovementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterMovement() {
        Product product = new Product();
        product.setId(1L);

        stockMovementService.registerMovement(product, 10, MovementType.INBOUND);

        verify(stockMovementRepository, times(1)).save(any(StockMovement.class));
    }
}
