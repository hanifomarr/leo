package com.selloohub.leo.order.controller;

import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.order.dto.OrderResponse;
import com.selloohub.leo.order.dto.UpdateOrderStatusRequest;
import com.selloohub.leo.order.service.AdminOrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable("id") String id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        OrderResponse response = adminOrderService.updateStatus(id, request.status());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/rerun-pipeline")
    public ResponseEntity<ApiResponse<OrderResponse>> rerunPipeline(
            @PathVariable("id") String id) {
        OrderResponse response = adminOrderService.rerunPipeline(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
