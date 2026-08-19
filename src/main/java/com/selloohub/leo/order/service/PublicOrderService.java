package com.selloohub.leo.order.service;

import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.common.util.PhoneNormalizer;
import com.selloohub.leo.order.dto.OrderStatusResponse;
import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class PublicOrderService {

    private final OrderRepository orderRepository;

    public PublicOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderStatusResponse getOrderStatus(String orderId, String phone) {
        String normalizedPhone = PhoneNormalizer.normalize(phone);

        Order order = orderRepository.findByOrderNoAndCustomerPhone(orderId, normalizedPhone)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return OrderStatusResponse.from(order);
    }
}
