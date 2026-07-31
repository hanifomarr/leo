package com.selloohub.leo.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ComboOptionRequest(
        @NotBlank
        String productId,

        @NotNull
        @PositiveOrZero
        BigDecimal additionalPrice
) {
}
