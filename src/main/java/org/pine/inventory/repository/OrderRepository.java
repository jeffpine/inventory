package org.pine.inventory.repository;

import org.pine.inventory.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    @Query("""
SELECT SUM(i.lineTotal) FROM Order o
JOIN o.items i
WHERE o.status = 'CONFIRMED'
AND o.confirmedAt between :start AND :end
""")
    BigDecimal totalSales(@Param("start")LocalDateTime start, @Param("end")LocalDateTime end);

    @Query("""
SELECT i.product.id AS productId, SUM(i.quantity) AS qty
FROM Order o JOIN o.items i
WHERE o.status = 'CONFIRMED'
AND o.confirmedAt between :start AND :end
GROUP BY i.product.id
order by qty desc 
""")
    List<Object[]> bestSellers(@Param("start")LocalDateTime start, @Param("end")LocalDateTime end);
}
