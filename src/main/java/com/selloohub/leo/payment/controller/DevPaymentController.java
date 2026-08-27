package com.selloohub.leo.payment.controller;

import com.selloohub.leo.common.response.ApiResponse;
import com.selloohub.leo.payment.model.PaymentSource;
import com.selloohub.leo.payment.service.PaymentWebhookService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dev/payments")
@Profile("dev")
public class DevPaymentController {

    private final PaymentWebhookService paymentWebhookService;

    public DevPaymentController(PaymentWebhookService paymentWebhookService) {
        this.paymentWebhookService = paymentWebhookService;
    }

    @PostMapping("/{billCode}/simulate-webhook")
    public ResponseEntity<ApiResponse<String>> simulateWebhook(@PathVariable("billCode") String billCode) {

        String response = paymentWebhookService.handlePaymentConfirmation(billCode, PaymentSource.WEBHOOK);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
