package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.request.CategoryRequest;
import com.ecommerce.inventory_service.request.ProductRequest;
import com.ecommerce.inventory_service.response.CategoryResponse;
import com.ecommerce.inventory_service.response.PageResponse;
import com.ecommerce.inventory_service.response.ProductResponse;
import com.ecommerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {
        return inventoryService.createCategory(request);
    }

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return inventoryService.getAllCategories();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return inventoryService.createProduct(request);
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProductById(@PathVariable UUID id) {
        return inventoryService.getProductById(id);
    }

    @GetMapping("/products/sku/{sku}")
    public ProductResponse getProductBySku(@PathVariable String sku) {
        return inventoryService.getProductBySku(sku);
    }

    // e.g. /api/inventory/products?categoryId=1&page=0&size=10&sort=price,asc
    @GetMapping("/products")
    public PageResponse<ProductResponse> getProducts(
            @RequestParam(required = false) Long categoryId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return inventoryService.getProducts(categoryId, pageable);
    }
}
