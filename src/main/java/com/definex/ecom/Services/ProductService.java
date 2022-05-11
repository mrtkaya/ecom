package com.definex.ecom.Services;

import com.definex.ecom.Entity.ProductRequest;

import java.util.Optional;


public interface ProductService {
    void addProduct(ProductRequest product);
    void deleteProduct(int productId);
    void updateProduct(ProductRequest product);
    Optional<ProductRequest> getProduct(int productId);
}
