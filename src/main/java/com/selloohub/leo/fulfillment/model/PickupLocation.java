package com.selloohub.leo.fulfillment.model;

import com.selloohub.leo.common.audit.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pickup_location")
public class PickupLocation extends Auditable {

    @Id
    private String id;
    private String name;
    private String address;
    private PickupLocationStatus status;

    public PickupLocation(String name, String address) {
        this.name = name;
        this.address = address;
        this.status = PickupLocationStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public PickupLocationStatus getStatus() {
        return status;
    }
}
