package com.ecommerce.inventory_service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Pattern(regexp = "^[A-Za-z]{2,10}$", message = "must be 2-10 letters") String code,
        @Size(max = 500) String description
) {
}
