package com.learning.payment.dto;

import com.learning.payment.entity.Customer;
import com.learning.payment.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    Integer id,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    Customer customer
) {
}
