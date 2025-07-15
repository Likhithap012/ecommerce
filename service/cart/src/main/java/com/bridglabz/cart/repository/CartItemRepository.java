package com.bridglabz.cart.repository;

import com.bridglabz.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByCustomerId(Integer customerId);
    Optional<CartItem> findByCustomerIdAndProductId(Integer customerId, Integer productId);
    boolean existsByCustomerIdAndProductId(Integer customerId, Integer productId);
}
