package com.selloohub.leo.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BundleComponentRequest(
        @NotBlank String productId,
        @Min(1) int quantity
) {
}
