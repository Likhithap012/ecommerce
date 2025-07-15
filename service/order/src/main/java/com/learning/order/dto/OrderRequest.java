package com.learning.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        Integer customerId,
        PaymentMethod paymentMethod
) {}
