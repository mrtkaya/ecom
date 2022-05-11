package com.definex.ecom.Services;

import com.definex.ecom.Entity.BasketRequest;
import com.definex.ecom.Entity.ProductRequest;
import com.definex.ecom.Model.Discount;

import java.util.List;

public interface BasketService {

    List<BasketRequest> listAllBaskets();
    List<BasketRequest> getAddedProductsInBasket(int customerId);
    void addAmountToTheProduct(BasketRequest basketRequest);
    void addNewProduct(ProductRequest product, int customerId);
    void removeWithAmount(BasketRequest basketRequest);
    void removeItemFromBasket(int bucketId);
    void updateDiscountPrice(Discount discountToApply);
}
