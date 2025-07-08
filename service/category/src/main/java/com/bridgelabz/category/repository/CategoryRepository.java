package com.bridgelabz.category.repository;

import com.bridgelabz.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}

