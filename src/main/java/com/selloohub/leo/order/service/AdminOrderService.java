package com.selloohub.leo.order.service;

import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.order.dto.OrderResponse;
import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;
import com.selloohub.leo.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminOrderService {

    private static final Set<OrderStatus> CANCELLABLE_FROM =
            Set.of(OrderStatus.PENDING_PAYMENT, OrderStatus.PAID);

    private final OrderRepository orderRepository;
    private final OrderPipelineService orderPipelineService;

    public AdminOrderService(OrderRepository orderRepository, OrderPipelineService orderPipelineService) {
        this.orderRepository = orderRepository;
        this.orderPipelineService = orderPipelineService;
    }

    public OrderResponse updateStatus(String orderId, OrderStatus requestedStatus) {
        return switch (requestedStatus) {
            case CANCELLED -> cancelOrder(orderId);
            case COMPLETED -> completeOrder(orderId);
            default ->
                    throw new ConflictException("ORDER_INVALID_STATE", "Unsupported status transition: " + requestedStatus);
        };
    }

    public OrderResponse cancelOrder(String orderId) {
        return transition(orderId, CANCELLABLE_FROM, OrderStatus.CANCELLED);
    }

    public OrderResponse completeOrder(String orderId) {
        return transition(orderId, Set.of(OrderStatus.READY_FOR_PICKUP), OrderStatus.COMPLETED);
    }

    public OrderResponse rerunPipeline(String orderId) {
        orderPipelineService.runPipeline(orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return OrderResponse.from(order);
    }


    private OrderResponse transition(String orderId, Set<OrderStatus> allowedFrom, OrderStatus to) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!allowedFrom.contains(order.getStatus())) {
            throw new ConflictException("ORDER_INVALID_STATE", "Cannot move to " + to + " from " + order.getStatus());
        }

        Order updated = orderRepository.transitionStatus(orderId, order.getStatus(), to)
                .orElseThrow(() -> new ConflictException("ORDER_INVALID_STATE", "Order status changed before transition could apply"));

        return OrderResponse.from(updated);
    }
}
