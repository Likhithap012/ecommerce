package com.bridglabz.cart.repository;

import com.bridglabz.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCustomerId(Integer customerId);

    Optional<CartItem> findByCustomerIdAndProductId(Integer customerId, Integer productId);

    void deleteByCustomerIdAndProductId(Integer customerId, Integer productId);

    void deleteByCustomerId(Integer customerId);
}
