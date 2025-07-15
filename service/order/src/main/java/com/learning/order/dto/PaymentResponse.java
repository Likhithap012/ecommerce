package com.learning.order.dto;

import java.math.BigDecimal;

public record PaymentResponse(
        Integer id,
        Integer customerId,
        Integer orderId,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        String status
) {}
