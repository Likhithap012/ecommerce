package com.bridgelabz.product.mapper;

import com.bridgelabz.product.dto.CategoryRequest;
import com.bridgelabz.product.dto.CategoryResponse;
import com.bridgelabz.product.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryRequest request) {
        if (request == null) return null;
        return Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        if (category == null) return null;
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
