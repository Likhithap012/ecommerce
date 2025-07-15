
package com.learning.order.repository;


import com.learning.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCustomerId(Integer customerId);

}