package com.bridgelabz.product.service;

import com.bridgelabz.product.dto.ProductPurchaseRequest;
import com.bridgelabz.product.dto.ProductPurchaseResponse;
import com.bridgelabz.product.dto.ProductRequest;
import com.bridgelabz.product.dto.ProductResponse;
import com.bridgelabz.product.entity.Category;
import com.bridgelabz.product.entity.Product;
import com.bridgelabz.product.exception.CategoryAlreadyExistsException;
import com.bridgelabz.product.exception.ProductAlreadyExistsException;
import com.bridgelabz.product.exception.ProductPurchaseException;
import com.bridgelabz.product.mapper.ProductMapper;
import com.bridgelabz.product.repository.CategoryRepository;
import com.bridgelabz.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) throws ProductAlreadyExistsException {
        System.out.println("Creating product with categoryId: " + request.categoryId());
        boolean exists = repository.existsByNameIgnoreCase(request.name());
        if (exists) {
            throw new ProductAlreadyExistsException("Category with name '" + request.name() + "' already exists.");
        }
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public ProductResponse findById(Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + id));

        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found for ID:: " + product.getCategoryId()));

        return mapper.toProductResponse(product, category);
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(product -> {
                    Category category = categoryRepository.findById(product.getCategoryId())
                            .orElseThrow(() -> new EntityNotFoundException("Category not found for ID:: " + product.getCategoryId()));
                    return mapper.toProductResponse(product, category);
                })
                .collect(Collectors.toList());
    }

    public void updateProduct(Integer id, ProductRequest request) {
        var existingProduct = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID:: " + id));

        existingProduct.setName(request.name());
        existingProduct.setDescription(request.description());
        existingProduct.setAvailableQuantity(request.availableQuantity());
        existingProduct.setPrice(request.price());
        existingProduct.setCategoryId(request.categoryId());

        repository.save(existingProduct);
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var sortedRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);

            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);

            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }
    public void deleteProduct(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with ID:: " + id);
        }
        repository.deleteById(id);
    }

}
