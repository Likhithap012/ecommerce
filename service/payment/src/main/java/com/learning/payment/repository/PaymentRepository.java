package com.learning.payment.repository;

import com.learning.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

   boolean existsByOrderId(Integer orderId) ;
}
