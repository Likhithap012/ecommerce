package com.learning.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderSummaryResponse(
        Integer orderId,
        String status,
        BigDecimal totalAmount,
        LocalDateTime orderDate,
        String paymentMethod,
        List<OrderItemSummary> items
) {
    public record OrderItemSummary(
            Integer productId,
            Integer quantity
    ) {}
}
