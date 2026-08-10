package com.selloohub.leo.stock.dto;

import com.selloohub.leo.stock.model.StockMovement;
import com.selloohub.leo.stock.model.StockMovementReason;

import java.time.Instant;

public record StockMovementResponse(

        String id,
        String productId,
        StockMovementReason reason,
        int delta,
        int resultingStockQty,
        String orderId,
        String note,
        String actor,
        Instant createdAt
) {

    public static StockMovementResponse from(StockMovement movement) {
        return new StockMovementResponse(
                movement.getId(),
                movement.getProductId(),
                movement.getReason(),
                movement.getDelta(),
                movement.getResultingStockQty(),
                movement.getOrderId(),
                movement.getNote(),
                movement.getActor(),
                movement.getCreatedAt()
        );
    }
}
