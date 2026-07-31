package com.selloohub.leo.product.model;

import com.selloohub.leo.common.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "product")
public class Product extends Auditable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String sku;

    private String name;
    private String description;
    private final ProductType type;
    private BigDecimal retailPrice;
    private Integer weightGrams;
    private List<String> imageUrls;
    private List<BundleComponent> components;
    private List<ComboOptionGroup> comboGroups;
    private long onHand;
    private ProductStatus status;


    public Product(String sku, String name, ProductType type, BigDecimal retailPrice) {
        this.sku = sku;
        this.name = name;
        this.type = type;
        this.retailPrice = retailPrice;
        this.status = ProductStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getType() {
        return type;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getWeightGrams() {
        return weightGrams;
    }

    public void setWeightGrams(Integer weightGrams) {
        this.weightGrams = weightGrams;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<BundleComponent> getComponents() {
        return components;
    }

    public void setComponents(List<BundleComponent> components) {
        this.components = components;
    }

    public List<ComboOptionGroup> getComboGroups() {
        return comboGroups;
    }

    public void setComboGroups(List<ComboOptionGroup> comboGroups) {
        this.comboGroups = comboGroups;
    }

    public long getOnHand() {
        return onHand;
    }

    void setOnHand(long onHand) {
        this.onHand = onHand;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    @Getter
    @AllArgsConstructor
    public static class BundleComponent {
        private String productId;
        private int quantity;
    }

    @Getter
    @AllArgsConstructor
    public static class ComboOptionGroup {
        private String groupName;
        private int chooseCount;
        private List<ComboOption> options;
    }

    @Getter
    @AllArgsConstructor
    public static class ComboOption {
        private String productId;
        private BigDecimal additionalPrice;
    }
}
