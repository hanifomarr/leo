package com.selloohub.leo.fulfillment.model;

import com.selloohub.leo.common.audit.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "fulfillment_config")
public class FulfillmentConfig extends Auditable {

    @Id
    private String id;

    @Indexed(unique = true)
    private FulfillmentType type;
    private boolean enabled;
    private BigDecimal fee;

    public FulfillmentConfig(FulfillmentType type, boolean enabled, BigDecimal fee) {
        this.type = type;
        this.enabled = enabled;
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public FulfillmentType getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public BigDecimal getFee() {
        return fee;
    }
}
