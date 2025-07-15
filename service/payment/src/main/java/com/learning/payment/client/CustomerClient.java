package com.learning.payment.client;

import com.learning.payment.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface CustomerClient {

    @GetMapping("/api/v1/customers/{id}")
    CustomerResponse findById(@PathVariable("id") Integer customerId);
}
