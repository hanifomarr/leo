package com.selloohub.leo.order.repository;

import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;

import java.util.Optional;

public interface OrderRepositoryCustom {
    Optional<Order> transitionStatus(String orderId, OrderStatus expected, OrderStatus next);
}
