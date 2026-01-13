package org.pine.inventory.repository;

import org.pine.inventory.domain.stock.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

@Query("""

select m from StockMovement m

where m.product.id = :productId

and m.occurredAt between :start and :end

order by m.occurredAt desc

""")
List<StockMovement> movementsForPeriod(@Param("productId") Long productId,

                                             @Param("start") LocalDateTime start,

                                             @Param("end") LocalDateTime end);

}