package com.learning.payment.mapper;

import com.learning.payment.dto.PaymentRequest;
import com.learning.payment.entity.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

  public Payment toPayment(PaymentRequest request) {
    return Payment.builder()
            .amount(request.amount())
            .paymentMethod(request.paymentMethod())
            .orderId(request.orderId())
            .customerId(request.customerId())
            .build();
  }
}
