package com.selloohub.leo.product.dto;

import com.selloohub.leo.product.model.ProductStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateProductStatusRequest(
        @NotNull ProductStatus status
) {
}
