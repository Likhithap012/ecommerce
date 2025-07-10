package com.learning.payment.service;

import com.learning.payment.dto.PaymentRequest;
import com.learning.payment.entity.Payment;
import com.learning.payment.exception.DuplicateOrderIdException;
import com.learning.payment.mapper.PaymentMapper;
import com.learning.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository repository;
  private final PaymentMapper mapper;

  public Payment createPayment(PaymentRequest request) {
    // Check if a payment with the same orderId already exists
    if (repository.existsByOrderId(request.orderId())) {
      throw new DuplicateOrderIdException("A payment already exists for orderId: " + request.orderId());
    }

    // Map DTO to Entity
    Payment payment = mapper.toPayment(request);

    return repository.save(payment);
  }

  public List<Payment> getAllPayments() {
    return repository.findAll();
  }
}
