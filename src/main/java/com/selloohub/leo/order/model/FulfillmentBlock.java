package com.selloohub.leo.order.model;

import com.selloohub.leo.fulfillment.model.FulfillmentType;

import java.math.BigDecimal;
import java.time.Instant;

public class FulfillmentBlock {

    private FulfillmentType type;
    private String locationId;
    private String slotId;
    private String address;
    private String postcode;
    private BigDecimal fee;
    private String trackingNo;
    private Instant dispatchedAt;

    public FulfillmentBlock(FulfillmentType type, String locationId, String slotId, String address, String postcode, BigDecimal fee) {
        this.type = type;
        this.locationId = locationId;
        this.slotId = slotId;
        this.address = address;
        this.postcode = postcode;
        this.fee = fee;
    }

    public FulfillmentType getType() {
        return type;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Instant getDispatchedAt() {
        return dispatchedAt;
    }

    public void setDispatchedAt(Instant dispatchedAt) {
        this.dispatchedAt = dispatchedAt;
    }
}
