package com.ecommerce.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items", indexes = {
        @Index(name = "idx_order_items_order", columnList = "order_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Reference to inventory-service product (no FK across services)
    @Column(nullable = false)
    private UUID productId;

    // Snapshot fields: order must not change if product is edited later
    @Column(nullable = false, length = 30)
    private String sku;

    @Column(nullable = false, length = 200)
    private String productName;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    // unitPrice * quantity
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal lineTotal;
}
