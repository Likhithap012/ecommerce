package com.learning.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.order.model.PaymentMethod;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer customerId
) {

}
