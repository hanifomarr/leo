package com.selloohub.leo.order.controller;

import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.order.dto.OrderStatusResponse;
import com.selloohub.leo.order.service.PublicOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/orders")
public class PublicOrderController {

    private final PublicOrderService publicOrderService;

    public PublicOrderController(PublicOrderService publicOrderService) {
        this.publicOrderService = publicOrderService;
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<ApiResponse<OrderStatusResponse>> getOrderStatus(
            @PathVariable("orderNo") String orderNo,
            @RequestParam String phone) {

        OrderStatusResponse response = publicOrderService.getOrderStatus(orderNo, phone);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }
}
