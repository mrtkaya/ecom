package com.definex.ecom.Services.ServicesImpl;

import com.definex.ecom.Entity.ProductRequest;
import com.definex.ecom.Repository.ProductRequestRepository;
import com.definex.ecom.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRequestRepository productRequestRepository;

    @Override
    public void addProduct(ProductRequest product) {
        productRequestRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
        productRequestRepository.deleteById(productId);
    }

    @Override
    public void updateProduct(ProductRequest product) {
        productRequestRepository.save(product);
    }

    @Override
    public Optional<ProductRequest> getProduct(int productId) {
        return productRequestRepository.findById(productId);
    }
}
