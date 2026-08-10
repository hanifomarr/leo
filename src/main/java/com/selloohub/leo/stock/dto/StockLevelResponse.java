package com.selloohub.leo.stock.dto;

import com.selloohub.leo.product.model.Product;

public record StockLevelResponse(
        String productId,
        String sku,
        String name,
        int stockQty
) {
    public static StockLevelResponse from(Product product) {
        return new StockLevelResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getStockQty()
        );
    }
}
