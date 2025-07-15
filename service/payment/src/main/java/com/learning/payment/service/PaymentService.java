package com.learning.payment.service;

import com.learning.payment.client.CustomerClient;
import com.learning.payment.dto.CustomerResponse;
import com.learning.payment.dto.PaymentRequest;
import com.learning.payment.entity.Payment;
import com.learning.payment.exception.CustomerNotFoundException;
import com.learning.payment.exception.DuplicateOrderIdException;
import com.learning.payment.mapper.PaymentMapper;
import com.learning.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final CustomerClient customerClient;

    public Payment createPayment(PaymentRequest request) {
        // 1. Check for duplicate payment
        if (repository.existsByOrderId(request.orderId())) {
            throw new DuplicateOrderIdException("A payment already exists for orderId: " + request.orderId());
        }

        // 2. Validate customer using Feign Client
        try {
            CustomerResponse customer = customerClient.findById(request.customerId());
            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found with ID: " + request.customerId());
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new CustomerNotFoundException("Customer with ID " + request.customerId() + " does not exist.");
        }

        // 3. Save payment
        Payment payment = mapper.toPayment(request);
        return repository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }
}
