package com.bridgelabz.category.repository;

import com.bridgelabz.category.dto.CategoryRequest;
import com.bridgelabz.category.dto.CategoryResponse;
import com.bridgelabz.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repo;

    public Integer create(CategoryRequest req) {
        Category category = Category.builder()
                .name(req.name())
                .description(req.description())
                .build();
        return repo.save(category).getId();
    }

    public List<CategoryResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(cat -> new CategoryResponse(cat.getId(), cat.getName(), cat.getDescription()))
                .toList();
    }

    public CategoryResponse getById(Integer id) {
        return repo.findById(id)
                .map(cat -> new CategoryResponse(cat.getId(), cat.getName(), cat.getDescription()))
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}

