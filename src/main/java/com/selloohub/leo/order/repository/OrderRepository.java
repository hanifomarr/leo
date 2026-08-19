package com.selloohub.leo.order.repository;

import com.selloohub.leo.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<Order> findByOrderNoAndCustomerPhone(String orderNo, String customerPhone);
}
