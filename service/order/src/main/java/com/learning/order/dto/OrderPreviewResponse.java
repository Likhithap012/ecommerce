package com.learning.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderPreviewResponse(
        Integer customerId,
        BigDecimal totalAmount,
        List<CartItemSummary> items
) {
    public record CartItemSummary(
            Integer productId,
            String productName,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {}
}