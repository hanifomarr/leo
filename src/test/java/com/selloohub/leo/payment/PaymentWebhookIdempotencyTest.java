package com.selloohub.leo.payment;

import com.jayway.jsonpath.JsonPath;
import com.selloohub.leo.AbstractIntegrationTest;
import com.selloohub.leo.fulfillment.model.FulfillmentConfig;
import com.selloohub.leo.fulfillment.model.FulfillmentType;
import com.selloohub.leo.fulfillment.model.PickupLocation;
import com.selloohub.leo.fulfillment.repository.FulfillmentConfigRepository;
import com.selloohub.leo.fulfillment.repository.PickupLocationRepository;
import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;
import com.selloohub.leo.order.repository.OrderRepository;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.repository.ProductRepository;
import com.selloohub.leo.stock.repository.StockMovementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentWebhookIdempotencyTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PickupLocationRepository pickupLocationRepository;

    @Autowired
    private FulfillmentConfigRepository fulfillmentConfigRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    private String productId;
    private String pickupLocationId;
    private String fulfillmentConfigId;
    private String orderId;

    @AfterEach
    void cleanup() {
        stockMovementRepository.deleteAll(
                stockMovementRepository.findByProductIdOrderByCreatedAtDesc(productId, Pageable.unpaged()));
        if (orderId != null) {
            orderRepository.deleteById(orderId);
        }
        if (fulfillmentConfigId != null) {
            fulfillmentConfigRepository.deleteById(fulfillmentConfigId);
        }
        if (pickupLocationId != null) {
            pickupLocationRepository.deleteById(pickupLocationId);
        }
        if (productId != null) {
            productRepository.deleteById(productId);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void duplicateWebhookProducesNoSecondSideEffect() throws Exception {
        Product product = productRepository.save(new Product("TEST-SKU-WEBHOOK", "Test Product", new BigDecimal("10.00")));
        productId = product.getId();
        productRepository.applyStockDelta(productId, 10);

        PickupLocation location = pickupLocationRepository.save(new PickupLocation("Test Location", "123 Test St"));
        pickupLocationId = location.getId();

        FulfillmentConfig config = fulfillmentConfigRepository.save(
                new FulfillmentConfig(FulfillmentType.PICKUP, true, BigDecimal.ZERO));
        fulfillmentConfigId = config.getId();

        MvcResult checkoutResult = mockMvc.perform(post("/api/v1/public/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "items": [{"productId": "%s", "qty": 2}],
                                  "customerName": "Test Customer",
                                  "customerPhone": "+60177777777",
                                  "pickupLocationId": "%s"
                                }
                                """.formatted(productId, pickupLocationId)))
                .andExpect(status().isCreated())
                .andReturn();

        String orderNo = JsonPath.read(checkoutResult.getResponse().getContentAsString(), "$.data.orderNo");

        Order order = orderRepository.findByOrderNoAndCustomerPhone(orderNo, "+60177777777").orElseThrow();
        orderId = order.getId();
        String billCode = order.getPayment().getBillCode();

        mockMvc.perform(post("/api/v1/admin/dev/payments/{billCode}/simulate-webhook", billCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("PAID, pipeline ran"));

        mockMvc.perform(post("/api/v1/admin/dev/payments/{billCode}/simulate-webhook", billCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("duplicate, acknowledged"));

        Order reloadedOrder = orderRepository.findById(orderId).orElseThrow();
        assertEquals(OrderStatus.READY_FOR_PICKUP, reloadedOrder.getStatus());

        Product reloadedProduct = productRepository.findById(productId).orElseThrow();
        assertEquals(8, reloadedProduct.getStockQty());

        long movementCount = stockMovementRepository
                .findByProductIdOrderByCreatedAtDesc(productId, Pageable.unpaged())
                .getTotalElements();
        assertEquals(1, movementCount);
    }
}