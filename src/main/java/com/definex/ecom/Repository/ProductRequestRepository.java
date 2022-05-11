package com.definex.ecom.Repository;

import com.definex.ecom.Entity.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, Integer> {
}
