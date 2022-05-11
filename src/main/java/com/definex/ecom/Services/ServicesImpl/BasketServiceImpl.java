package com.definex.ecom.Services.ServicesImpl;

import com.definex.ecom.Entity.BasketRequest;
import com.definex.ecom.Entity.ProductRequest;
import com.definex.ecom.Model.Discount;
import com.definex.ecom.Repository.BasketRequestRepository;
import com.definex.ecom.Services.BasketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private static final Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);

    private final BasketRequestRepository basketRequestRepository;

    @Override
    public List<BasketRequest> listAllBaskets() {
        return basketRequestRepository.findAll();
    }

    @Override
    public List<BasketRequest> getAddedProductsInBasket(int customerId) {
        return basketRequestRepository.getBasketRequestByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void addAmountToTheProduct(BasketRequest basketRequest) {
        basketRequestRepository.save(basketRequest);
    }

    @Override
    public void addNewProduct(ProductRequest product, int customerId) {
        BasketRequest basketRequest = new BasketRequest();
        basketRequest.setProductId(product.getProductId());
        basketRequest.setAmount(product.getAmount());
        basketRequest.setCustomerId(customerId);
        basketRequest.setDiscountValue(product.getDiscountValue());
        basketRequest.setDiscountWithCode(product.isDiscountWithCode());
        basketRequestRepository.save(basketRequest);
    }

    @Override
    public void removeWithAmount(BasketRequest basketRequest) {
        basketRequestRepository.save(basketRequest);
    }

    @Override
    public void removeItemFromBasket(int basketId) {
        basketRequestRepository.deleteById( basketId);
    }

    @Override
    @Transactional
    public void updateDiscountPrice(Discount discountToApply) {
        List<BasketRequest> list = getAddedProductsInBasket(discountToApply.getCustomerId());
        for(BasketRequest basket:list){
            if(basket.getProductId() != discountToApply.getProductId()) {
                basket.setLastDiscountPrice(0);
                basket.setDiscountType("");
                basketRequestRepository.updateDiscountPrice(basket.getLastDiscountPrice(), basket.getCustomerId(), basket.getProductId(), basket.getDiscountType(), basket.getBasketId());
                logger.info("UpdateDiscountPrice - discount is changeTo new discountType old productId. BasketId:"+basket.getBasketId());
            }else{
                basket.setLastDiscountPrice(discountToApply.getDiscountPrice());
                basket.setDiscountType(discountToApply.getType());
                basketRequestRepository.updateDiscountPrice(basket.getLastDiscountPrice(), basket.getCustomerId(), basket.getProductId(), basket.getDiscountType(), basket.getBasketId());
                logger.info("UpdateDiscountPrice - discount is changeTo new discountType. BasketId:"+basket.getBasketId());
            }
        }
    }

}
