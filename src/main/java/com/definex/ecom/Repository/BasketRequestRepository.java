package com.definex.ecom.Repository;

import com.definex.ecom.Entity.BasketRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRequestRepository extends JpaRepository<BasketRequest, Integer> {

    @Modifying
    @Query("Select basketId, customerId, productId, amount, discountWithCode, discountType, lastDiscountPrice from Basket ")
    List<BasketRequest> getAll();

    List<BasketRequest> getBasketRequestByCustomerId(int customerId);


    @Modifying
    @Query("Update Basket set lastDiscountPrice = :lastDiscountPrice where  customerId = :customerId and productId = :productId and discountType = :discountType and basketId = :basketId ")
    void updateDiscountPrice(@Param("lastDiscountPrice") double lastDiscountPrice, @Param("customerId") int customerId, @Param("productId")int productId, @Param("discountType") String discountType, @Param("basketId") int basketId);
}
