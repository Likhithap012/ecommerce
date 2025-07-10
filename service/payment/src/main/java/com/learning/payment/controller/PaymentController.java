package com.learning.payment.controller;

import com.learning.payment.entity.Payment;
import com.learning.payment.dto.PaymentRequest;
import com.learning.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService service;


  @PostMapping
  public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest request) {
    Payment savedPayment = service.createPayment(request);
    return ResponseEntity.ok(savedPayment.getId());
  }

  @GetMapping
  public ResponseEntity<List<Payment>> getAllPayments() {
    return ResponseEntity.ok(service.getAllPayments());
  }

}
