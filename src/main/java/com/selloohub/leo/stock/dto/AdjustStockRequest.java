package com.selloohub.leo.stock.dto;

import com.selloohub.leo.stock.model.StockMovementReason;
import jakarta.validation.constraints.NotBlank;

public record AdjustStockRequest(
        int delta,
        StockMovementReason reason,
        @NotBlank String note
) {
}
