package com.selloohub.leo.product.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CreateBundleProductRequest(
        @NotBlank
        String sku,

        @NotBlank
        @Size(max = 200)
        String name,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal retailPrice,

        @NotEmpty
        @Size(max = 20)
        List<@Valid  BundleComponentRequest> components
) {
}
