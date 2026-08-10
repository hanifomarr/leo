package com.selloohub.leo.stock.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "stock_movement")
public class StockMovement {

    @Id
    private String id;
    @Indexed
    private String productId;
    private StockMovementReason reason;
    private int delta;
    private int resultingStockQty;
    private String orderId;
    private String note;
    private String actor;
    @CreatedDate
    private Instant createdAt;

    public StockMovement(String productId, StockMovementReason reason, int delta, int resultingStockQty, String orderId, String note, String actor) {
        this.productId = productId;
        this.reason = reason;
        this.delta = delta;
        this.resultingStockQty = resultingStockQty;
        this.orderId = orderId;
        this.note = note;
        this.actor = actor;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public StockMovementReason getReason() {
        return reason;
    }

    public int getDelta() {
        return delta;
    }

    public int getResultingStockQty() {
        return resultingStockQty;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getNote() {
        return note;
    }

    public String getActor() {
        return actor;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
