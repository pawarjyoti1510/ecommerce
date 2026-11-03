package com.ecommerce.order_service.entity;

public enum OrderStatus {
    PENDING,          // order created, stock reserved, waiting for payment
    CONFIRMED,        // payment successful
    PAYMENT_FAILED,   // payment declined, stock released
    CANCELLED,        // cancelled by customer before shipping
    SHIPPED,
    DELIVERED
}
