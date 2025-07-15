package com.bridglabz.cart.entity;

import com.bridglabz.cart.dto.CartItemResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "customer_id"}))
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    private Integer quantity;
}
