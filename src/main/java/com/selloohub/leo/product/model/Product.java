package com.selloohub.leo.product.model;

import com.selloohub.leo.common.audit.Auditable;
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
    private BigDecimal retailPrice;
    private Integer weightGrams;
    private List<String> imageUrls;
    private int stockQty;
    private ProductStatus status;


    public Product(String sku, String name, BigDecimal retailPrice) {
        this.sku = sku;
        this.name = name;
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

    public int getStockQty() {
        return stockQty;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
