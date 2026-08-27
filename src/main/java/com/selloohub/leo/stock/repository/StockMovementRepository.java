package com.selloohub.leo.stock.repository;

import com.selloohub.leo.stock.model.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends MongoRepository<StockMovement, String> {

    Page<StockMovement> findByProductIdOrderByCreatedAtDesc(String productId, Pageable pageable);

    boolean existsByProductId(String productId);

    boolean existsByOrderIdAndProductId(String orderId, String productId);
}
