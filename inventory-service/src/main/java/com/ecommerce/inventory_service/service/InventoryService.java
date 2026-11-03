package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.request.CategoryRequest;
import com.ecommerce.inventory_service.request.ProductRequest;
import com.ecommerce.inventory_service.response.CategoryResponse;
import com.ecommerce.inventory_service.response.PageResponse;
import com.ecommerce.inventory_service.response.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    CategoryResponse createCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(UUID id);

    ProductResponse getProductBySku(String sku);

    PageResponse<ProductResponse> getProducts(Long categoryId, Pageable pageable);
}
