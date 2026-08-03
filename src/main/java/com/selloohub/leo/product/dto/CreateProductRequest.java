package com.selloohub.leo.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(
        @NotBlank
        String sku,

        @NotBlank
        @Size(max = 200)
        String name,

        @Size(max = 2000)
        String description,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal retailPrice,

        @Positive
        Integer weightGrams,

        List<String> imageUrls
) {
}
