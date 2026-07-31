package com.selloohub.leo.product.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ComboOptionGroupRequest(
        @NotBlank
        String groupName,

        @Min(1)
        int chooseCount,

        @NotEmpty
        List<@Valid ComboOptionRequest> options
) {
}
