package com.selloohub.leo.fulfillment;

import com.selloohub.leo.common.util.MoneyMath;
import com.selloohub.leo.fulfillment.model.FulfillmentConfig;
import com.selloohub.leo.fulfillment.model.FulfillmentType;
import com.selloohub.leo.fulfillment.model.PickupLocation;
import com.selloohub.leo.fulfillment.repository.FulfillmentConfigRepository;
import com.selloohub.leo.fulfillment.repository.PickupLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConditionalOnProperty(name = "app.fulfillment.bootstrap.enabled", havingValue = "true")
public class FulfillmentBootstrapRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FulfillmentBootstrapRunner.class);
    private final PickupLocationRepository pickupLocationRepository;
    private final FulfillmentConfigRepository fulfillmentConfigRepository;

    public FulfillmentBootstrapRunner(PickupLocationRepository pickupLocationRepository, FulfillmentConfigRepository fulfillmentConfigRepository) {
        this.pickupLocationRepository = pickupLocationRepository;
        this.fulfillmentConfigRepository = fulfillmentConfigRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (!pickupLocationRepository.existsByName("Main Warehouse")) {
            PickupLocation pickupLocation = new PickupLocation("Main Warehouse", "Seri Kembangan");
            pickupLocationRepository.save(pickupLocation);
            log.info("Pickup location seeded: {}", pickupLocation.getName());
        } else {
            log.info("Fulfillment bootstrap skipped — pickup location 'Main Warehouse' already exists");
        }

        if (!fulfillmentConfigRepository.existsByType(FulfillmentType.PICKUP)) {
            FulfillmentConfig fulfillmentConfig = new FulfillmentConfig(FulfillmentType.PICKUP, true, MoneyMath.round2(BigDecimal.ZERO));
            fulfillmentConfigRepository.save(fulfillmentConfig);
            log.info("Fulfillment config seeded: {}", fulfillmentConfig.getType());
        } else {
            log.info("Fulfillment bootstrap skipped — config for type '{}' already exists", FulfillmentType.PICKUP);
        }

    }
}
