package com.learning.order.client;

import com.learning.order.dto.PaymentRequest;
import com.learning.order.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}"
)
public interface PaymentClient {

    @PostMapping
    Integer createPayment(@RequestBody PaymentRequest request);
    @GetMapping("/order/{orderId}")
    PaymentResponse getPaymentByOrderId(@PathVariable Integer orderId);


}
