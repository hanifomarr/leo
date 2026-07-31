package com.selloohub.leo.product.dto;

import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import com.selloohub.leo.product.model.ProductType;


import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(

        String id,
        String sku,
        String name,
        String description,
        ProductType type,
        BigDecimal retailPrice,
        Integer weightGrams,
        List<String> imageUrls,
        List<BundleComponentResponse> components,
        List<ComboOptionGroupResponse> comboGroups,
        long onHand,
        ProductStatus status
) {

    public record BundleComponentResponse(
            String productId,
            int quantity) {
    }

    public record ComboOptionGroupResponse(
            String groupName,
            int chooseCount,
            List<ComboOptionResponse> options
    ) {
    }

    public record ComboOptionResponse(
            String productId,
            BigDecimal additionalPrice
    ) {
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getType(),
                product.getRetailPrice(),
                product.getWeightGrams(),
                product.getImageUrls(),
                mapComponents(product.getComponents()),
                mapCombos(product.getComboGroups()),
                product.getOnHand(),
                product.getStatus()
        );
    }

    private static List<BundleComponentResponse> mapComponents(List<Product.BundleComponent> components) {
        if (components == null) return null;
        return components.stream()
                .map(c -> new BundleComponentResponse(c.getProductId(), c.getQuantity()))
                .toList();
    }

    private static List<ComboOptionGroupResponse> mapCombos(List<Product.ComboOptionGroup> groups) {
        if (groups == null) return null;
        return groups.stream()
                .map(g -> new ComboOptionGroupResponse(
                        g.getGroupName(),
                        g.getChooseCount(),
                        g.getOptions().stream()
                                .map(o -> new ComboOptionResponse(o.getProductId(), o.getAdditionalPrice()))
                                .toList()))
                .toList();
    }
}
