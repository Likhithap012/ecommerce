package com.bridglabz.cart.service;


import com.bridglabz.cart.client.CustomerClient;
import com.bridglabz.cart.client.ProductClient;
import com.bridglabz.cart.dto.AddToCartRequest;
import com.bridglabz.cart.dto.CartItemResponse;
import com.bridglabz.cart.dto.CustomerResponse;
import com.bridglabz.cart.dto.ProductResponse;
import com.bridglabz.cart.entity.CartItem;
import com.bridglabz.cart.exceptions.CartItemNotFoundException;
import com.bridglabz.cart.exceptions.CustomerNotFoundException;
import com.bridglabz.cart.exceptions.InvalidCartOperationException;
import com.bridglabz.cart.exceptions.ProductNotFoundException;
import com.bridglabz.cart.mapper.CartMapper;
import com.bridglabz.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository repository;
    private final CartMapper mapper;
    private final ProductClient productClient;
    private final CustomerClient customerClient;

    public Integer addToCart(AddToCartRequest request) {
        // Step 1: Validate if product exists
        Optional<CustomerResponse> customerResponse;
        try {
            customerResponse = customerClient.findCustomerById(request.customerId());
        } catch (Exception e) {
            throw new CustomerNotFoundException("Unable to validate customer: " + e.getMessage());
        }
        ProductResponse product;
        try {
            product = productClient.getProductById(request.productId());
        } catch (Exception e) {
            throw new ProductNotFoundException("Product with ID " + request.productId() + " not found.");
        }

        // Step 2: Optional - Check available quantity
        if (request.quantity() > product.availableQuantity()) {
            throw new InvalidCartOperationException("Requested quantity exceeds available stock.");
        }

        // Step 3: Check if item already exists
        if (repository.existsByCustomerIdAndProductId(request.customerId(), request.productId())) {
            throw new InvalidCartOperationException("Product already exists in cart");
        }

        // Step 4: Save to cart
        var entity = mapper.toEntity(request);
        var saved = repository.save(entity);
        return saved.getId();
    }

    public List<CartItemResponse> getCartByCustomer(Integer customerId) {
        return repository.findAllByCustomerId(customerId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void removeItem(Integer cartItemId) {
        if (!repository.existsById(cartItemId)) {
            throw new CartItemNotFoundException("No cart item found with ID: " + cartItemId);
        }
        repository.deleteById(cartItemId);
    }

}
