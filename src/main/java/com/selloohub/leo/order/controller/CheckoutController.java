package com.selloohub.leo.order.controller;

import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.order.dto.CheckoutRequest;
import com.selloohub.leo.order.dto.CheckoutResponse;
import com.selloohub.leo.order.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CheckoutResponse>> checkout(
            @Valid @RequestBody CheckoutRequest request,
            @RequestParam(required = false) String ref) {

        CheckoutResponse response = checkoutService.checkout(request, ref);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }
}
