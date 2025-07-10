package com.learning.order.service;

import com.learning.order.customer.CustomerResponse;
import com.learning.order.dto.OrderLineRequest;
import com.learning.order.dto.OrderRequest;
import com.learning.order.dto.OrderResponse;
import com.learning.order.exception.*;
import com.learning.order.mapper.OrderMapper;
import com.learning.order.payment.PaymentClient;
import com.learning.order.payment.PaymentRequest;
import com.learning.order.customer.CustomerClient;
import com.learning.order.dto.PurchaseRequest;
import com.learning.order.product.ProductClient;
import com.learning.order.repository.OrderRepository;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        CustomerResponse customer;
        try {
            customer = customerClient.findCustomerById(request.customerId())
                    .orElseThrow(() -> new CustomerNotFoundException("No customer found with the provided ID: " + request.customerId()));
        } catch (FeignException.NotFound ex) {
            throw new CustomerNotFoundException("No customer found with the provided ID: " + request.customerId());
        }

        var purchasedProducts = productClient.purchaseProducts(request.products());


        // Save order with duplicate check
        var order = mapper.toOrder(request);
        try {
            order = repository.save(order);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() != null && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException sqlEx) {
                if (sqlEx.getMessage().contains("UK3n2tbuf4k1male5dqi8rojcaj")) {
                    throw new DuplicateOrderReferenceException("Order reference already exists.");
                }
            }
            throw ex;
        }

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        try {
            paymentClient.requestOrderPayment(paymentRequest);
        } catch (Exception ex) {
            throw new PaymentServiceException("Failed to process payment: " + ex.getMessage());
        }

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with ID: %d", id)));
    }

    public List<OrderResponse> findOrdersByCustomerId(Integer customerId) {
        return repository.findAllByCustomerId(customerId)
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        if (!repository.existsById(orderId)) {
            throw new EntityNotFoundException("Order with ID " + orderId + " not found");
        }

        orderLineService.deleteByOrderId(orderId);
        repository.deleteById(orderId);
    }
}
