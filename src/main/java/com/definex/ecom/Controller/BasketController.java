package com.definex.ecom.Controller;

import com.definex.ecom.ConstantsAndEnums.Constants;
import com.definex.ecom.ConstantsAndEnums.DiscountType;
import com.definex.ecom.ControllersHandler.Exceptions.CustomException;
import com.definex.ecom.Entity.BasketRequest;
import com.definex.ecom.Entity.ProductRequest;
import com.definex.ecom.Model.Basket;
import com.definex.ecom.Model.Discount;
import com.definex.ecom.Services.BasketService;
import com.definex.ecom.Services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final BasketService basketService;
    private final ProductService productService;

    public BasketController(BasketService basketService, ProductService productService) {
        this.basketService = basketService;
        this.productService = productService;
    }

    @RequestMapping(value = {"/basket/addItemToBasket"}, produces = "application/json", method = RequestMethod.POST)
    public void addItemToBucket(@RequestBody Basket basket){
        logger.info("Controller:  createCustomer");
        List<BasketRequest> listAddedProducts = basketService.getAddedProductsInBasket(basket.getCustomerId());
        addToBasket(listAddedProducts, basket.getProductRequest(),  basket.getCustomerId());
        findDiscountTypesAndApply( basket.getCustomerId());
    }

    @RequestMapping(value = {"/basket/removeItemFromBasket"}, produces = "application/json", method = RequestMethod.POST)
    public void removeItemFromBucket(@RequestBody Basket basket) {
        logger.info("Controller:  removeItemFromExistingBucket");
        List<BasketRequest> listAddedProducts = basketService.getAddedProductsInBasket(basket.getCustomerId());
        if(!listAddedProducts.isEmpty()) {
            removeFromBasket(listAddedProducts, basket.getProductRequest());
            findDiscountTypesAndApply(basket.getCustomerId());
        }else{
            logger.error("There is no product to delete in the basket.");
            throw new CustomException("No product to remove in basket.", HttpStatus.BAD_REQUEST);
        }
    }

    private void addToBasket(List<BasketRequest> listAddedProducts, ProductRequest product, int customerId) {
        boolean added = false;
        for (BasketRequest addedProduct : listAddedProducts) {
            if (addedProduct.getProductId() == product.getProductId()) {
                addedProduct.setAmount(addedProduct.getAmount()+product.getAmount());
                basketService.addAmountToTheProduct(addedProduct);
                added = true;
                break;
            }
        }
        if(!added) {
            basketService.addNewProduct(product, customerId);
        }
    }

    private void removeFromBasket(List<BasketRequest> listAddedProducts, ProductRequest product) {
        for (BasketRequest addedProduct : listAddedProducts) {
            if (addedProduct.getProductId() == product.getProductId()) {
                if (addedProduct.getAmount() == 0) {
                    logger.error("removeFromBasket - Trying to remove product from no product! ");
                } else if (addedProduct.getAmount() == 1) {
                    basketService.removeItemFromBasket(addedProduct.getBasketId());
                } else {
                    if(addedProduct.getAmount() > product.getAmount()){
                        addedProduct.setAmount(addedProduct.getAmount()-product.getAmount());
                        basketService.removeWithAmount(addedProduct);
                    }else{
                        basketService.removeItemFromBasket(addedProduct.getBasketId());
                    }
                }
                break;
            }
        }
    }

    @RequestMapping(value = {"/basket/listAllBaskets"}, produces = "application/json", method = RequestMethod.GET)
    public List<BasketRequest> listAllBaskets() {
        logger.info("Controller:  listAllBaskets");
        return basketService.listAllBaskets();
    }

    private void findDiscountTypesAndApply(int customerId) {
        List<BasketRequest> listAddedProducts = basketService.getAddedProductsInBasket(customerId);
        List<Discount> discounts = new ArrayList<>();
        int cheapestProduct = 0;
        double cheapestProductPrice = Double.MAX_VALUE;
        int cheapestProductAmount = 1;

        double noDiscountPriceTotal = 0.0;
        for(BasketRequest addedProduct: listAddedProducts){
            Optional<ProductRequest> product = productService.getProduct(addedProduct.getProductId());

            discounts.add(calculate3ProductDiscount(product, addedProduct));

            discounts.add(calculateBuy1Get1Discount(product, customerId));

            discounts.add(calculateWithCode(addedProduct, product));

            if(cheapestProductPrice > product.get().getPrice()){
                cheapestProductPrice = product.get().getPrice();
                cheapestProduct = product.get().getProductId();
                cheapestProductAmount = product.get().getAmount();
            }

            noDiscountPriceTotal = noDiscountPriceTotal + product.get().getAmount() * product.get().getPrice();
        }

        discounts.add(calculateOverThousand(noDiscountPriceTotal, cheapestProduct, cheapestProductPrice, cheapestProductAmount, customerId));

        Discount discountToApply = chooseTheBiggestDiscount(discounts);

        basketService.updateDiscountPrice(discountToApply);
    }

    private Discount calculateOverThousand(double noDiscountPriceTotal, int cheapestProduct, double cheapestProductPrice, int cheapestProductAmount, int customerId) {
        Discount discount = new Discount();
        if(noDiscountPriceTotal > Constants.DISCOUNT_IF_OVER_PRICE){
            discount.setProductId(cheapestProduct);
            discount.setDiscountPrice(cheapestProductPrice * Constants.DISCOUNT_PER_OVER_1000 * cheapestProductAmount);
            discount.setType(DiscountType.DISCOUNT_20_PER_OVER_1000.toString());
            discount.setCustomerId(customerId);
            return discount;
        }
        return null;
    }

    private Discount calculate3ProductDiscount(Optional<ProductRequest> product, BasketRequest addedProduct) {
        Discount discount;
        if ((addedProduct.getAmount() / 3) > 0) {
            int discountPercentage = (addedProduct.getAmount() / 3) * Constants.DISCOUNT_PER_OF_THREE_ITEM;
            discount = new Discount();
            discount.setProductId(product.get().getProductId());
            discount.setDiscountPrice(calculate3ItemDiscount(product, addedProduct.getAmount(), discountPercentage));
            discount.setType(DiscountType.GET_3_DISCOUNT_15_PER_ON_3_ITEM.toString());
            discount.setCustomerId(addedProduct.getCustomerId());
            return discount;
        }
        return null;
    }

    private Discount calculateBuy1Get1Discount(Optional<ProductRequest> product, int customerId) {
        Discount discount = new Discount();
        if(product.get().isBuy1get1() ){
            discount.setProductId(product.get().getProductId());
            discount.setDiscountPrice(product.get().getPrice() * (product.get().getAmount() / 2)) ;
            discount.setType(DiscountType.BUY_1_GET_1.toString());
            discount.setCustomerId(customerId);
            return discount;
        }
        return  null;
    }

    private Discount calculateWithCode(BasketRequest addedProduct, Optional<ProductRequest> product) {
        Discount discount = new Discount();
        if(addedProduct.isDiscountWithCode()){
            discount.setDiscountPrice(product.get().getPrice() * product.get().getAmount() * Constants.CODE_DISCOUNT);
            discount.setProductId(product.get().getProductId());
            discount.setType(DiscountType.PROMO_CODE_TO_CHEAPEST_ITEM.toString());
            discount.setCustomerId(addedProduct.getCustomerId());
            return discount;
        }
        return null;
    }

    private double calculate3ItemDiscount(Optional<ProductRequest> product, int amount, int discountPercentage){
        return product.get().getPrice() * amount * discountPercentage;
    }

    private Discount chooseTheBiggestDiscount(List<Discount> discounts) {
        double biggestDiscount = 0;
        int discountedProduct = 0;
        String type = null;
        int customerId = 0;
        for( Discount discount : discounts){
            if(discount != null ) {
                if (discount.getDiscountPrice() > biggestDiscount) {
                    biggestDiscount = discount.getDiscountPrice();
                    discountedProduct = discount.getProductId();
                    type = discount.getType();
                    customerId = discount.getCustomerId();
                }
            }
        }

        Discount discount = new Discount();
        discount.setDiscountPrice(biggestDiscount);
        discount.setProductId(discountedProduct);
        discount.setType(type);
        discount.setCustomerId(customerId);

        return discount;
    }


}
