package com.bridgelabz.product.mapper;

import com.bridgelabz.product.dto.ProductPurchaseResponse;
import com.bridgelabz.product.dto.ProductRequest;
import com.bridgelabz.product.dto.ProductResponse;
import com.bridgelabz.product.entity.Category;
import com.bridgelabz.product.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

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

    public ProductResponse toProductResponse(Product product, Category category) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
