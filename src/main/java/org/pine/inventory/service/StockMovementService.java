package org.pine.inventory.service;

import org.pine.inventory.domain.Product;
import org.pine.inventory.domain.stock.MovementType;
import org.pine.inventory.domain.stock.StockMovement;
import org.pine.inventory.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

@Service
public class StockMovementService {

    private final StockMovementRepository repository;

    public StockMovementService(StockMovementRepository repository) {
        this.repository = repository;
    }

    public void registerMovement(Product product, int quantity, MovementType type) {
        StockMovement movement = new StockMovement(
                product,
                quantity,
                type
        );

        repository.save(movement);
    }
}
