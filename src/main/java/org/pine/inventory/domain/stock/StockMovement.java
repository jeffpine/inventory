package org.pine.inventory.domain.stock;

import jakarta.persistence.*;
import org.pine.inventory.domain.Product;

import java.time.LocalDateTime;

@Entity

@Table(name = "stock_movements")

public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "product_id", nullable = false)

    private Product product;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)

    private MovementType type; // INBOUND, OUTBOUND, ADJUSTMENT

    @Column(nullable = false)

    private Integer quantity;

    private String reason;

    @Column(nullable = false)

    private LocalDateTime occurredAt;

    public StockMovement(Product product, int quantity, MovementType type) {
        this.product = product;
        this.quantity = quantity;
        this.type = type;
        this.occurredAt = LocalDateTime.now();
    }

    public StockMovement() {
        this.occurredAt = LocalDateTime.now();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}