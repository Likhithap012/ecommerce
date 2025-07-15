package com.learning.order.service;

import com.learning.order.client.CartClient;
import com.learning.order.client.PaymentClient;
import com.learning.order.client.ProductClient;
import com.learning.order.dto.*;
import com.learning.order.entity.Order;
import com.learning.order.entity.OrderItem;
import com.learning.order.exception.*;
import com.learning.order.mapper.OrderMapper;
import com.learning.order.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderMapper orderMapper;

    public OrderSuccessResponse placeOrder(OrderRequest request) {
        List<CartItemResponse> cartItems = cartClient.getCartItems(request.customerId());
        if (cartItems.isEmpty()) {
            throw new CartEmptyException("Cart is empty for customer: " + request.customerId());
        }

        // Create order items and reduce stock (no stock check as per request)
        List<OrderItem> orderItems = cartItems.stream().map(item -> {
            ProductResponse product = productClient.getProductById(item.productId());
            productClient.reduceStock(product.id(), item.quantity()); // directly reduce
            return orderMapper.toOrderItem(item, product);
        }).toList();

        // Calculate total
        BigDecimal totalAmount = cartItems.stream()
                .map(item -> {
                    ProductResponse product = productClient.getProductById(item.productId());
                    return product.price().multiply(BigDecimal.valueOf(item.quantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Save order
        Order order = orderMapper.toOrder(request, orderItems, totalAmount);
        orderItems.forEach(item -> item.setOrder(order));
        Order savedOrder = orderRepository.save(order);

        // Check if payment already exists
        try {
            paymentClient.getPaymentByOrderId(savedOrder.getId());
            throw new PaymentFailedException("Payment already exists for order ID: " + savedOrder.getId());
        } catch (FeignException.NotFound e) {
            // Proceed with payment
            try {
                paymentClient.createPayment(new PaymentRequest(
                        totalAmount,
                        request.paymentMethod(),
                        savedOrder.getId(),
                        request.customerId()
                ));
            } catch (Exception ex) {
                throw new PaymentFailedException("Payment failed: " + ex.getMessage());
            }
        } catch (Exception e) {
            throw new PaymentFailedException("Error verifying existing payment: " + e.getMessage());
        }

        // Clear cart after payment
        try {
            cartClient.clearCart(request.customerId());
        } catch (FeignException.NotFound e) {
            log.warn("Cart already empty for customerId: {}", request.customerId());
        }

        log.info("Order placed successfully for customerId={}, orderId={}", request.customerId(), savedOrder.getId());

        return new OrderSuccessResponse(
                "Order placed successfully",
                savedOrder.getId(),
                savedOrder.getCustomerId()
        );
    }

    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if ("CANCELLED".equalsIgnoreCase(order.getStatus())) {
            throw new InvalidOrderOperationException("Order is already cancelled");
        }

        order.setStatus("CANCELLED");
        order.getItems().forEach(item ->
                productClient.increaseStock(item.getProductId(), item.getQuantity()));
        orderRepository.save(order);
    }

    public OrderPreviewResponse previewOrder(Integer customerId) {
        List<CartItemResponse> cartItems = cartClient.getCartItems(customerId);
        if (cartItems.isEmpty()) {
            throw new CartEmptyException("Cart is empty for customer: " + customerId);
        }

        List<OrderPreviewResponse.CartItemSummary> itemSummaries = cartItems.stream().map(item -> {
            ProductResponse product = productClient.getProductById(item.productId());
            BigDecimal subtotal = product.price().multiply(BigDecimal.valueOf(item.quantity()));
            return new OrderPreviewResponse.CartItemSummary(
                    product.id(), product.name(), item.quantity(), product.price(), subtotal);
        }).collect(Collectors.toList());

        BigDecimal total = itemSummaries.stream()
                .map(OrderPreviewResponse.CartItemSummary::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderPreviewResponse(null, customerId, total, itemSummaries);
    }

    public BigDecimal getTotalAmount(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId))
                .getTotalAmount();
    }

    public List<OrderSummaryResponse> getOrdersByCustomer(Integer customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        return orders.stream()
                .map(orderMapper::toSummaryResponse)
                .toList();
    }

    public List<OrderSummaryResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toSummaryResponse)
                .toList();
    }
}
