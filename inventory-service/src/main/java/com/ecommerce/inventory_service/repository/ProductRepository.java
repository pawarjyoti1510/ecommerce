package com.ecommerce.inventory_service.repository;

import com.ecommerce.inventory_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @EntityGraph(attributePaths = "category")
    Optional<Product> findBySku(String sku);

    @EntityGraph(attributePaths = "category")
    Optional<Product> findWithCategoryById(UUID id);

    @EntityGraph(attributePaths = "category")
    Page<Product> findAllBy(Pageable pageable);

    @EntityGraph(attributePaths = "category")
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    boolean existsBySku(String sku);
}
