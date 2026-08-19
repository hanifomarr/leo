package com.selloohub.leo.order.model;

import java.math.BigDecimal;
import java.util.List;

public class OrderLine {

    private String productId;
    private String skuSnapshot;
    private String nameSnapshot;
    private OrderLineType type;
    private int qty;
    private BigDecimal unitPrice;
    private PriceSource priceSource;
    private String ruleOrCampaignId;
    private BigDecimal lineTotal;

    // COMBO only — null for SIMPLE/BUNDLE
    private BigDecimal basePriceSnapshot;
    private List<SelectedOption> selectedOptions;

    public OrderLine(String productId, String skuSnapshot, String nameSnapshot, OrderLineType type, int qty, BigDecimal unitPrice, PriceSource priceSource, String ruleOrCampaignId, BigDecimal lineTotal, BigDecimal basePriceSnapshot, List<SelectedOption> selectedOptions) {
        this.productId = productId;
        this.skuSnapshot = skuSnapshot;
        this.nameSnapshot = nameSnapshot;
        this.type = type;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.priceSource = priceSource;
        this.ruleOrCampaignId = ruleOrCampaignId;
        this.lineTotal = lineTotal;
        this.basePriceSnapshot = basePriceSnapshot;
        this.selectedOptions = selectedOptions;
    }

    public String getProductId() {
        return productId;
    }

    public String getSkuSnapshot() {
        return skuSnapshot;
    }

    public String getNameSnapshot() {
        return nameSnapshot;
    }

    public OrderLineType getType() {
        return type;
    }

    public int getQty() {
        return qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public PriceSource getPriceSource() {
        return priceSource;
    }

    public String getRuleOrCampaignId() {
        return ruleOrCampaignId;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public BigDecimal getBasePriceSnapshot() {
        return basePriceSnapshot;
    }

    public List<SelectedOption> getSelectedOptions() {
        return selectedOptions;
    }
}
