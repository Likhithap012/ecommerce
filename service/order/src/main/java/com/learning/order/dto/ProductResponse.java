package com.learning.order.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        double availableQuantity,
        BigDecimal price
) {}