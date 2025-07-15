// OrderSuccessResponse.java
package com.learning.order.dto;

import com.learning.order.entity.Order;

public record OrderSuccessResponse(
        String message,
        Integer orderId,
        Integer customerId
) {}
