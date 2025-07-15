package com.learning.payment.dto;

import com.learning.payment.entity.PaymentMethod;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PaymentRequest(

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Order ID is required")
        Integer orderId,

        @NotNull(message = "Customer ID is required")
        Integer customerId

) {}
