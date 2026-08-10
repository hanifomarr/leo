package com.selloohub.leo.stock.controller;

import com.selloohub.leo.auth.security.AppUserDetails;
import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.common.response.PageResponse;
import com.selloohub.leo.stock.dto.AdjustStockRequest;
import com.selloohub.leo.stock.dto.StockLevelResponse;
import com.selloohub.leo.stock.dto.StockMovementResponse;
import com.selloohub.leo.stock.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PageResponse<StockLevelResponse>>> getAllStockLevels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<StockLevelResponse> response = stockService.getStockLevels(page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/adjust")
    public ResponseEntity<ApiResponse<StockMovementResponse>> adjustStockMovement(
            @PathVariable("id") String id,
            @Valid @RequestBody AdjustStockRequest request,
            @AuthenticationPrincipal AppUserDetails principal) {
        StockMovementResponse response = stockService.adjustStock(id, request, principal.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}/movements")
    public ResponseEntity<ApiResponse<PageResponse<StockMovementResponse>>> getMovements(
            @PathVariable("id") String id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageResponse<StockMovementResponse> response = stockService.getMovements(id, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
