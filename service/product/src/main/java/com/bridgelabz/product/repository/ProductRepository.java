package com.bridgelabz.product.repository;

import com.bridgelabz.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByIdInOrderById(List<Integer> ids);
    boolean existsByNameIgnoreCase(String name);
}
