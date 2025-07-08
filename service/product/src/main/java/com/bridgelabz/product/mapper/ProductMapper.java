package com.bridgelabz.product.mapper;

import com.bridgelabz.product.client.CategoryClient;
import com.bridgelabz.product.dto.*;
import com.bridgelabz.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryClient categoryClient;

    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .categoryId(request.categoryId())
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        var category = categoryClient.getCategoryById(product.getCategoryId());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                category.id(),
                category.name(),
                category.description()
        );
    }

    public ProductPurchaseResponse toproductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
