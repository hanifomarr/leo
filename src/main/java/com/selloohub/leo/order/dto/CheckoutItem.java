package com.selloohub.leo.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CheckoutItem(
        @NotBlank String productId,
        @Positive int qty
) {
}
