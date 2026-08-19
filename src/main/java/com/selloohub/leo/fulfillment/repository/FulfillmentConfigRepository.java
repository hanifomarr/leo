package com.selloohub.leo.fulfillment.repository;

import com.selloohub.leo.fulfillment.model.FulfillmentConfig;
import com.selloohub.leo.fulfillment.model.FulfillmentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FulfillmentConfigRepository extends MongoRepository<FulfillmentConfig, String> {
    boolean existsByType(FulfillmentType type);

    Optional<FulfillmentConfig> findByType(FulfillmentType type);
}
