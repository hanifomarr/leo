package com.selloohub.leo.fulfillment.repository;

import com.selloohub.leo.common.repository.SoftDeleteRepository;
import com.selloohub.leo.fulfillment.model.PickupLocation;
import com.selloohub.leo.fulfillment.model.PickupLocationStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupLocationRepository extends SoftDeleteRepository<PickupLocation, String, PickupLocationStatus> {
    boolean existsByName(String name);
}
