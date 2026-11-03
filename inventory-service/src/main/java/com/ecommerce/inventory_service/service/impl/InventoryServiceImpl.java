package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.entity.Category;
import com.ecommerce.inventory_service.entity.Product;
import com.ecommerce.inventory_service.entity.ProductStatus;
import com.ecommerce.inventory_service.exception.DuplicateResourceException;
import com.ecommerce.inventory_service.exception.InvalidRequestException;
import com.ecommerce.inventory_service.exception.ResourceNotFoundException;
import com.ecommerce.inventory_service.repository.CategoryRepository;
import com.ecommerce.inventory_service.repository.ProductRepository;
import com.ecommerce.inventory_service.request.CategoryRequest;
import com.ecommerce.inventory_service.request.ProductRequest;
import com.ecommerce.inventory_service.response.CategoryResponse;
import com.ecommerce.inventory_service.response.PageResponse;
import com.ecommerce.inventory_service.response.ProductResponse;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryServiceImpl implements InventoryService {

    private static final String DEFAULT_CURRENCY = "INR";

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Category already exists with name: " + request.name());
        }
        if (categoryRepository.existsByCodeIgnoreCase(request.code())) {
            throw new DuplicateResourceException("Category already exists with code: " + request.code());
        }
        Category category = Category.builder()
                .name(request.name().trim())
                .code(request.code().toUpperCase())
                .description(request.description())
                .build();
        Category saved = categoryRepository.save(category);
        log.info("Created category id={} code={}", saved.getId(), saved.getCode());
        return toCategoryResponse(saved);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toCategoryResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if (request.price().compareTo(request.mrp()) > 0) {
            throw new InvalidRequestException("price cannot be greater than mrp");
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.categoryId()));

        Product product = Product.builder()
                .sku(generateSku(category.getCode()))
                .name(request.name().trim())
                .brand(request.brand())
                .summary(request.summary())
                .description(request.description())
                .category(category)
                .price(request.price())
                .mrp(request.mrp())
                .currency(request.currency() == null ? DEFAULT_CURRENCY : request.currency())
                .stockQuantity(request.stockQuantity())
                .imageUrl(request.imageUrl())
                .status(ProductStatus.ACTIVE)
                .build();
        Product saved = productRepository.save(product);
        log.info("Created product id={} sku={}", saved.getId(), saved.getSku());
        return toProductResponse(saved);
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        return productRepository.findWithCategoryById(id)
                .map(this::toProductResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public ProductResponse getProductBySku(String sku) {
        return productRepository.findBySku(sku.toUpperCase())
                .map(this::toProductResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "sku", sku));
    }

    @Override
    public PageResponse<ProductResponse> getProducts(Long categoryId, Pageable pageable) {
        Page<Product> page = categoryId == null
                ? productRepository.findAllBy(pageable)
                : productRepository.findByCategoryId(categoryId, pageable);
        return PageResponse.from(page.map(this::toProductResponse));
    }

    // SKU format: <CATEGORY_CODE>-<8 hex chars>, e.g. ELEC-7F3A9C21
    private String generateSku(String categoryCode) {
        String sku;
        do {
            sku = categoryCode + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (productRepository.existsBySku(sku));
        return sku;
    }

    private int discountPercent(BigDecimal price, BigDecimal mrp) {
        return mrp.subtract(price)
                .multiply(BigDecimal.valueOf(100))
                .divide(mrp, 0, RoundingMode.HALF_UP)
                .intValue();
    }

    private CategoryResponse toCategoryResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getCode(), c.getDescription());
    }

    private ProductResponse toProductResponse(Product p) {
        return new ProductResponse(
                p.getId(), p.getSku(), p.getName(), p.getBrand(), p.getSummary(), p.getDescription(),
                toCategoryResponse(p.getCategory()),
                p.getPrice(), p.getMrp(), discountPercent(p.getPrice(), p.getMrp()), p.getCurrency(),
                p.getStockQuantity(), p.getStockQuantity() > 0, p.getImageUrl(), p.getStatus(),
                p.getCreatedAt(), p.getUpdatedAt());
    }
}
