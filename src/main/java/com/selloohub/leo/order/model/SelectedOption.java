package com.selloohub.leo.order.model;

import java.math.BigDecimal;

public class SelectedOption {

    private String groupName;
    private String productId;
    private String nameSnapshot;
    private BigDecimal additionalPriceSnapshot;

    public SelectedOption(String groupName, String productId, String nameSnapshot, BigDecimal additionalPriceSnapshot) {
        this.groupName = groupName;
        this.productId = productId;
        this.nameSnapshot = nameSnapshot;
        this.additionalPriceSnapshot = additionalPriceSnapshot;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getProductId() {
        return productId;
    }

    public String getNameSnapshot() {
        return nameSnapshot;
    }

    public BigDecimal getAdditionalPriceSnapshot() {
        return additionalPriceSnapshot;
    }
}
