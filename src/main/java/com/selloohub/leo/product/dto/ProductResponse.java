package com.selloohub.leo.product.dto;

import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;


import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(

        String id,
        String sku,
        String name,
        String description,
        BigDecimal retailPrice,
        Integer weightGrams,
        List<String> imageUrls,
        int stockQty,
        ProductStatus status
) {


    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getRetailPrice(),
                product.getWeightGrams(),
                product.getImageUrls(),
                product.getStockQty(),
                product.getStatus()
        );
    }
}
