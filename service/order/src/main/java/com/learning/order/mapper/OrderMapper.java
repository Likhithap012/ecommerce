package com.learning.order.mapper;

import com.learning.order.dto.CartItemResponse;
import com.learning.order.dto.OrderRequest;

import com.learning.order.dto.OrderSummaryResponse;
import com.learning.order.dto.ProductResponse;

import com.learning.order.entity.Order;
import com.learning.order.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {

    public OrderItem toOrderItem(CartItemResponse item, ProductResponse product) {
        return OrderItem.builder()
                .productId(item.productId()).quantity(item.quantity())
                .build();
    }

    public Order toOrder(OrderRequest request, List<OrderItem> items, BigDecimal totalAmount) {
        return Order.builder()
                .customerId(request.customerId())
                .paymentMethod(request.paymentMethod())
                .orderDate(LocalDateTime.now())
                .status("PLACED")
                .totalAmount(totalAmount)
                .items(items)
                .build();
    }
    public OrderSummaryResponse toSummaryResponse(Order order) {
        List<OrderSummaryResponse.OrderItemSummary> itemSummaries = order.getItems().stream()
                .map(item -> new OrderSummaryResponse.OrderItemSummary(item.getProductId(), item.getQuantity()))
                .toList();

        return new OrderSummaryResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getOrderDate(),
                order.getPaymentMethod().name(),
                itemSummaries
        );
    }

}
