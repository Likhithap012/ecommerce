package com.bridglabz.cart.repository;

import com.bridglabz.cart.entity.Cart;
import com.bridglabz.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}