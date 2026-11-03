package com.ecommerce.inventory_service.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 200) String name,
        @Size(max = 100) String brand,
        @Size(max = 500) String summary,
        String description,
        @NotNull Long categoryId,
        @NotNull @DecimalMin(value = "0.01") @Digits(integer = 10, fraction = 2) BigDecimal price,
        @NotNull @DecimalMin(value = "0.01") @Digits(integer = 10, fraction = 2) BigDecimal mrp,
        @Pattern(regexp = "^[A-Z]{3}$", message = "must be a 3-letter code like INR") String currency,
        @NotNull @PositiveOrZero Integer stockQuantity,
        String imageUrl
) {
}
