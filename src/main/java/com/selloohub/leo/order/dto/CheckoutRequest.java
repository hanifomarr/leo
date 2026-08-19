package com.selloohub.leo.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CheckoutRequest(

        @NotEmpty List<@Valid CheckoutItem> items,
        @NotBlank String customerName,
        @NotBlank String customerPhone,
        @NotBlank String pickupLocationId
) {
}
