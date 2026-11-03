package com.ecommerce.inventory_service.response;

public record CategoryResponse(
        Long id,
        String name,
        String code,
        String description
) {
}
