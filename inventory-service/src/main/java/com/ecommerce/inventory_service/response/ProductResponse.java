package com.ecommerce.inventory_service.response;

import com.ecommerce.inventory_service.entity.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String sku,
        String name,
        String brand,
        String summary,
        String description,
        CategoryResponse category,
        BigDecimal price,
        BigDecimal mrp,
        Integer discountPercent,
        String currency,
        Integer stockQuantity,
        boolean inStock,
        String imageUrl,
        ProductStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
