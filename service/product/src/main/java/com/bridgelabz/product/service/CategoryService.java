package com.bridgelabz.product.service;

import com.bridgelabz.product.dto.CategoryRequest;
import com.bridgelabz.product.dto.CategoryResponse;
import com.bridgelabz.product.entity.Category;
import com.bridgelabz.product.exception.CategoryAlreadyExistsException;
import com.bridgelabz.product.mapper.CategoryMapper;
import com.bridgelabz.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public Integer create(CategoryRequest request) {
        boolean exists = repository.existsByNameIgnoreCase(request.name());
        if (exists) {
            throw new CategoryAlreadyExistsException("Category with name '" + request.name() + "' already exists.");
        }
        Category category = mapper.toCategory(request);
        return repository.save(category).getId();
    }

    public List<CategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CategoryResponse getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
