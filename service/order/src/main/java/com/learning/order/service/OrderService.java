package com.learning.order.service;

import com.learning.order.dto.OrderLineRequest;
import com.learning.order.dto.OrderRequest;
import com.learning.order.dto.OrderResponse;
import com.learning.order.exception.BusinessException;
import com.learning.order.mapper.OrderMapper;
import com.learning.order.model.Order;
import com.learning.order.payment.PaymentClient;
import com.learning.order.payment.PaymentRequest;
import com.learning.order.customer.CustomerClient;
import com.learning.order.dto.PurchaseRequest;
import com.learning.order.product.ProductClient;
import com.learning.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = repository.save(mapper.toOrder(request));

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

        paymentClient.requestOrderPayment(paymentRequest);

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

        // Step 1: Delete all order lines for this order
        orderLineService.deleteByOrderId(orderId);

        // Step 2: Delete the order itself
        repository.deleteById(orderId);
    }

}
