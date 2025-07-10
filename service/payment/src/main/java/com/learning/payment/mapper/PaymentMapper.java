package com.learning.payment.mapper;

import com.learning.payment.dto.PaymentRequest;
import com.learning.payment.entity.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

  public Payment toPayment(PaymentRequest request) {
    if (request == null) {
      return null;
    }
    return Payment.builder()
            .paymentMethod(request.paymentMethod())
            .amount(request.amount())
            .orderId(request.orderId())
            .build();
  }

}

